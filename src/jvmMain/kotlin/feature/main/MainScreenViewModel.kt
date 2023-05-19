package feature.main

import androidx.compose.runtime.LaunchedEffect
import common.view.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import network.isInternetAvailable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext

class MainScreenViewModel(private val scope: CoroutineScope)  {

    private val _tradingLog = MutableStateFlow<List<String>>(emptyList())
    val tradingLog: StateFlow<List<String>> = _tradingLog.asStateFlow()

    private val _onlineStatus = MutableStateFlow<Boolean>(false)
    val onlineStatus : StateFlow<Boolean> = _onlineStatus

    init {
        observeLog()
        observeNetworkConnection()
    }

    private fun observeLog() {
        scope.launch(Dispatchers.IO) {

            val mutableList = ArrayList<String>()

            for (i in 0..9) {
                val event: String = "${getTime()} | Test event $i"
                mutableList.add(event)
                println("Add to log ${mutableList.size}: Thread ${Thread.currentThread().name}")

                _tradingLog.update { ArrayList(mutableList.reversed()) }

                Thread.sleep(500)

                println("list size ${mutableList.size}")
            }

        }


    }

    private fun observeNetworkConnection() {
        scope.launch {
            isInternetAvailable().collectLatest { online ->
                _onlineStatus.update { online }
            }
        }
    }

    private fun getTime(): Long = Calendar.getInstance(Locale.getDefault()).timeInMillis
}