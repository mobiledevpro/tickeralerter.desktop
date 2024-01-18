import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.mobiledevpro"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
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
                implementation(project(Deps.Feature.HOME))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(Deps.Testing.COROUTINES)
                implementation(Deps.Testing.KOIN)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.mobiledevpro.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "tickeralerter"
            packageVersion = "1.0.0"
        }
    }
}