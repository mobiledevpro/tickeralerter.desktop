plugins {
    id("kotlin-multiplatform")
    kotlin("plugin.serialization")
}

val ktorVersion: String = extra["ktor.version"] as String

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client:$ktorVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

                api("io.ktor:ktor-client-okhttp:$ktorVersion")
                api("io.ktor:ktor-client-websockets:$ktorVersion")
                api("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            }
        }
    }
}

