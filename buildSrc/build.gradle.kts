plugins {
    `kotlin-dsl`
}

repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.4.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
}