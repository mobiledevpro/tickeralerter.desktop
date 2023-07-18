package com.mobiledepro.main.ext

import org.koin.core.qualifier.TypeQualifier
import org.koin.ext.getFullName
import org.koin.java.KoinJavaComponent

inline fun <reified T : Any> injectScope(): Lazy<T> {

    val scopeId = T::class.getFullName() + "@" + T::class.hashCode()
    val qualifier = TypeQualifier(T::class)

    println("SCOPE ID: $scopeId")
    println("SCOPE NAME: $qualifier")

    return KoinJavaComponent.getKoin().getOrCreateScope(scopeId, qualifier).inject()
}
