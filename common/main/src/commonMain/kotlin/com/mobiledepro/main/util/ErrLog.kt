package com.mobiledepro.main.util

inline fun <reified T : Any> Throwable.toLog() {
    println("::ERROR [${T::class.simpleName}] ${this.localizedMessage}")
}