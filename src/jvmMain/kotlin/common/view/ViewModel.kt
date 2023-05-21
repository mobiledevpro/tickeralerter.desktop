package common.view

import common.domain.interactor.MainScreenInteractor
import kotlinx.coroutines.CoroutineScope

open class ViewModel(
    private val interactor: MainScreenInteractor
) {
    open lateinit var vmScope: CoroutineScope

    open fun init(scope: CoroutineScope) {
        this.vmScope = scope
    }
}