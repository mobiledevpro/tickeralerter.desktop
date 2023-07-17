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
                implementation(project(":common:main"))
                api(project(":feature:tickerlist"))
                api(project(":feature:watchlist"))
                api(project(":feature:chart"))
                api(project(":feature:alert_settings"))
            }
        }
    }
}


