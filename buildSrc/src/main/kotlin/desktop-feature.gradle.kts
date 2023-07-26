import org.gradle.kotlin.dsl.*

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
        named("jvmMain") {
            dependencies {
                with(Deps.Common) {
                    api(project(MAIN))
                }
            }
        }
    }
}
