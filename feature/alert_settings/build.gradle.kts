plugins {
    id("desktop-feature")
}

kotlin.sourceSets {
    val jvmMain by getting {
        dependencies {
            with(Deps.Feature) {
                api(project(ALERT_TRIGGERS))
            }
        }
    }
}
