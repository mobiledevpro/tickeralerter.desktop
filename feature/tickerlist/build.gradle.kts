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
                api(project(":feature:watchlist"))
            }
        }
    }
}


