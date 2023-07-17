plugins {
    id("kotlin-multiplatform")
    id("org.jetbrains.compose")
}
val composeVersion: String = extra["compose.version"] as String

kotlin {
    jvm {
        jvmToolchain(Deps.JDK)
        withJava()
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                api(compose.desktop.currentOs)
                //Extended libraries pack
                api("org.jetbrains.compose.material:material-icons-extended-desktop:$composeVersion")
            }
        }
    }
}