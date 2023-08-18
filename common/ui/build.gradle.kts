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
                api(compose.desktop.currentOs)
                //Extended libraries pack
                api(Deps.Compose.MATERIAL_ICONS_EXTENDED_PACK)
            }
        }
    }
}