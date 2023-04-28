package common.view

import kotlinx.coroutines.CoroutineScope

open class ViewModel {
    open lateinit var vmScope: CoroutineScope

    open fun init(scope: CoroutineScope) {
        this.vmScope = scope
    }
}