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
                    api(project(TICKER_LIST))
                    api(project(CHART))
                    api(project(ALERTS))
                }
            }
        }
    }
}


