plugins {
    id("kotlin-multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        withJava()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":common:ui"))
                api(project(":common:database"))
                api(project(":common:network"))
            }
        }

        val jvmMain by getting
    }
}
