plugins {
    id("kotlin-multiplatform")
    kotlin("plugin.serialization")
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

