object Versions {
    const val KOTLIN = "1.9.0"
    const val COMPOSE = "1.5.0"

    const val KOIN = "3.2.0"
    const val SQL_DELIGHT = "2.0.0-alpha05"
    const val KTOR = "2.3.3"
    const val COROUTINES = "1.7.3"
}

object Deps {

    const val JDK = 17

    object Compose {
        const val MATERIAL_ICONS_EXTENDED_PACK =
            "org.jetbrains.compose.material:material-icons-extended-desktop:${Versions.COMPOSE}"
    }

    object Koin {
        const val CORE = "io.insert-koin:koin-core:${Versions.KOIN}"
    }

    object SQLDelight {
        const val DRIVER = "app.cash.sqldelight:sqlite-driver:${Versions.SQL_DELIGHT}"
        const val RUNTIME = "app.cash.sqldelight:runtime:${Versions.SQL_DELIGHT}"
        const val COROUTINE_EXT = "app.cash.sqldelight:coroutines-extensions:${Versions.SQL_DELIGHT}"
    }

    object Ktor {
        const val CLIENT = "io.ktor:ktor-client:${Versions.KTOR}"
        const val CLIENT_CORE = "io.ktor:ktor-client-core:${Versions.KTOR}"
        const val CLIENT_LOGGING = "io.ktor:ktor-client-logging:${Versions.KTOR}"
        const val CLIENT_NEGOTIATION = "io.ktor:ktor-client-content-negotiation:${Versions.KTOR}"

        const val CLIENT_OKHTTP = "io.ktor:ktor-client-okhttp:${Versions.KTOR}"
        const val CLIENT_WEBSOCKETS = "io.ktor:ktor-client-websockets:${Versions.KTOR}"
        const val SERIALIZATION_JSON = "io.ktor:ktor-serialization-kotlinx-json:${Versions.KTOR}"
    }

    object Testing {
        const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
        const val KOIN = "io.insert-koin:koin-test:${Versions.KOIN}"
    }


    object Common {
        const val MAIN = ":common:main"
        const val UI = ":common:ui"
        const val DATABASE = ":common:database"
        const val NETWORK = ":common:network"
    }

    object Feature {
        const val HOME = ":feature:home"
        const val TICKER_LIST = ":feature:tickerlist"
        const val CHART = ":feature:chart"
        const val ALERTS = ":feature:alerts"
        const val ALERT_EVENTS = ":feature:alert_events"
        const val ALERT_TRIGGERS = ":feature:alert_triggers"
        const val ALERT_SETTINGS = ":feature:alert_settings"
        const val WATCHLIST = ":feature:watchlist"
        const val ORDERS = ":feature:orders"
        const val ACCOUNT = ":feature:account"
    }

}
