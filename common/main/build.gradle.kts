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

                with(Deps.Common) {
                    api(project(UI))
                    api(project(DATABASE))
                    api(project(NETWORK))
                }
            }
        }

        val jvmMain by getting
    }
}
