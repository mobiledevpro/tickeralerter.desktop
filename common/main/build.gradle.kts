plugins {
    id("kotlin-multiplatform")
}

kotlin {
    jvm {
        withJava()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // implementation(project(":common:utils"))
                //  implementation(project(":common:database"))
                // implementation(Deps.ArkIvanov.Decompose.decompose)
                // implementation(Deps.ArkIvanov.MVIKotlin.mvikotlin)
                // implementation(Deps.ArkIvanov.MVIKotlin.mvikotlinExtensionsReaktive)
                // implementation(Deps.Badoo.Reaktive.reaktive)
            }
        }

        val commonTest by getting {
            dependencies {
                //   implementation(Deps.ArkIvanov.MVIKotlin.mvikotlinMain)
                //   implementation(Deps.Badoo.Reaktive.reaktiveTesting)
                //   implementation(Deps.Badoo.Reaktive.utils)
            }
        }
    }
}
