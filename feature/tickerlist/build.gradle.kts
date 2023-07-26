plugins {
    id("desktop-feature")
}

kotlin.sourceSets {
        val jvmMain by getting {
            dependencies {
                api(project(Deps.Feature.WATCHLIST))
            }
        }
}


