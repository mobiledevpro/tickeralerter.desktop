plugins {
    id("desktop-feature")
}

kotlin.sourceSets {
        val jvmMain by getting {
            dependencies {
                with(Deps.Feature) {
                    api(project(TICKER_LIST))
                    api(project(CHART))
                    api(project(ALERTS))
                    api(project(ORDERS))
                    api(project(ACCOUNT))
                }
            }
        }
    }


