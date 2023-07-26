plugins {
    id("desktop-feature")
}

kotlin.sourceSets {
        val jvmMain by getting {
            dependencies {
                with(Deps.Feature) {
                    api(project(ALERT_SETTINGS))
                    api(project(ALERT_TRIGGERS))
                    api(project(ALERT_EVENTS))
                }
            }
        }
    }

