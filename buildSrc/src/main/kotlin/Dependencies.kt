object Versions {
    const val KOIN = "3.2.0"
    const val SQL_DELIGHT = "2.0.0-alpha05"
}

object Deps {

    const val JDK = 17

    object Koin {
        const val CORE = "io.insert-koin:koin-core:${Versions.KOIN}"
    }

    object SQLDelight {
        const val DRIVER = "app.cash.sqldelight:sqlite-driver:${Versions.SQL_DELIGHT}"
        const val RUNTIME = "app.cash.sqldelight:runtime:${Versions.SQL_DELIGHT}"
        const val COROUTINE_EXT = "app.cash.sqldelight:coroutines-extensions:${Versions.SQL_DELIGHT}"
    }

}
