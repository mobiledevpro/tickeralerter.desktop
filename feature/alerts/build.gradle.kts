plugins {
    id("kotlin-multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        jvmToolchain(Deps.JDK)
        withJava()
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(Deps.Common.MAIN))

                with(Deps.Feature) {
                    api(project(ALERT_SETTINGS))
                    api(project(ALERT_TRIGGERS))
                    api(project(ALERT_EVENTS))
                }
            }
        }
    }
}

