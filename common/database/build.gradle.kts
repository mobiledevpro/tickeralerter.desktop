plugins {
    id("kotlin-multiplatform")
    id("app.cash.sqldelight")
}

val sqlDelightVersion: String = extra["sqldelight.version"] as String

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //Database
                implementation("app.cash.sqldelight:sqlite-driver:$sqlDelightVersion")
                api("app.cash.sqldelight:runtime:$$sqlDelightVersion")
                api("app.cash.sqldelight:coroutines-extensions:$sqlDelightVersion")

            }
        }

        val jvmMain by getting
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.mobiledevpro.database")
        }
    }
}


