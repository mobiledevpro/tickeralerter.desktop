plugins {
    id("kotlin-multiplatform")
    id("app.cash.sqldelight")
}

kotlin {
    jvm {
        jvmToolchain(Deps.JDK)
        withJava()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.Koin.CORE)

                //Database
                with(Deps.SQLDelight) {
                    implementation(DRIVER)
                    api(RUNTIME)
                    api(COROUTINE_EXT)
                }

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


