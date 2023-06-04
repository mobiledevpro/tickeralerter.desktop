plugins {
    id("kotlin-multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":common:ui"))
                api(project(":common:database"))
                api(project(":common:network"))

            }
        }
    }
}


