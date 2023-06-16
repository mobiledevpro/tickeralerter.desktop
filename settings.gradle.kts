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

        kotlin("multiplatform").version(kotlinVersion)
        kotlin("plugin.serialization").version(kotlinVersion)
        id("org.jetbrains.compose").version(composeVersion)
        id("app.cash.sqldelight").version(sqldelightVersion)
    }
}

rootProject.name = "tickeralerter"

include(
    ":common:database",
    ":common:network",
    ":common:ui",
    ":common:main",
    ":desktop",
    ":feature:tickerlist",
    ":feature:watchlist",
    ":feature:chart"
)
