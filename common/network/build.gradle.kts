import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    id("kotlin-multiplatform")
    kotlin("plugin.serialization")
    id("com.codingfeline.buildkonfig")
}

kotlin {
    jvm {
        jvmToolchain(Deps.JDK)
        withJava()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.Koin.CORE)

                with(Deps.Ktor) {
                    implementation(CLIENT)
                    implementation(CLIENT_CORE)
                    implementation(CLIENT_LOGGING)
                    implementation(CLIENT_NEGOTIATION)
                    api(CLIENT_OKHTTP)
                    api(CLIENT_WEBSOCKETS)
                    api(SERIALIZATION_JSON)
                }
            }
        }
    }
}

val apiKeyTestnet: String = extra["api.key.testnet"] as String
val apiKeyLive: String = extra["api.key.live"] as String

val apiSecretTestnet :  String = extra["api.secret.testnet"] as String
val apiSecretLive :  String = extra["api.secret.live"] as String

buildkonfig {
    packageName = "com.mobiledevpro.network"

    defaultConfigs {
        buildConfigField(STRING, "apiKeyTestnet", apiKeyTestnet)
        buildConfigField(STRING, "apiKeyLive", apiKeyLive)
        buildConfigField(STRING, "apiSecretTestnet", apiSecretTestnet)
        buildConfigField(STRING, "apiSecretLive", apiSecretLive)
    }
}

