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
                api(compose.desktop.currentOs)
            }
        }
    }
}