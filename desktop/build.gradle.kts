import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val ktorVersion: String = extra["ktor.version"] as String
val sqlDelightVersion: String = extra["sqldelight.version"] as String

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
    id("app.cash.sqldelight")
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
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                // implementation(project(":common"))
                implementation(compose.desktop.currentOs)
                // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4")
                implementation("io.ktor:ktor-client:$ktorVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                //Database
                implementation("app.cash.sqldelight:sqlite-driver:$sqlDelightVersion")
                implementation("app.cash.sqldelight:runtime:$$sqlDelightVersion")
                implementation("app.cash.sqldelight:coroutines-extensions:$sqlDelightVersion")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "tickeralerter"
            packageVersion = "1.0.0"
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.mobiledevpro")
            // sourceFolders.set(listOf("src/jvmMain/sqldelight"))
        }
    }
}
