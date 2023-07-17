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
        val commonMain by getting {
            dependencies {
                api(Deps.Koin.CORE)
                api(project(":common:ui"))
                api(project(":common:database"))
                api(project(":common:network"))
            }
        }

        val jvmMain by getting
    }
}
