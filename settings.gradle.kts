pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val composeVersion = extra["compose.version"] as String
        val sqldelightVersion = extra["sqldelight.version"] as String
        val buildKonfig = extra["buildkonfig.version"] as String

        kotlin("multiplatform").version(kotlinVersion)
        kotlin("plugin.serialization").version(kotlinVersion)
        id("org.jetbrains.compose").version(composeVersion)
        id("app.cash.sqldelight").version(sqldelightVersion)
        id("com.codingfeline.buildkonfig").version(buildKonfig)
    }
}
include(
    "desktop",
    ":common:database",
    ":common:network",
    ":common:ui",
    ":common:main",
    ":feature:home",
    ":feature:tickerlist",
    ":feature:watchlist",
    ":feature:chart",
    ":feature:alerts",
    ":feature:alert_events",
    ":feature:alert_triggers",
    ":feature:alert_settings",
    ":feature:orders",
    ":feature:account"
)

rootProject.name = "tickeralerter"

//this allows to add properties from a custom 'key.properties' file to the project properties
gradle.beforeProject {
    val localPropertiesFile = rootDir.resolve("key.properties")
    if (localPropertiesFile.exists()) {
        val localProperties = java.util.Properties()
        localProperties.load(localPropertiesFile.inputStream())
        localProperties.forEach { (k, v) -> if (k is String) project.extra.set(k, v) }
    }
}
