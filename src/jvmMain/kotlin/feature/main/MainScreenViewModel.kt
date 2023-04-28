package feature.main

import androidx.compose.runtime.LaunchedEffect
import common.view.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext

class MainScreenViewModel(private val scope: CoroutineScope) {

    private val _tradingLog = MutableStateFlow<List<String>>(emptyList())
    val tradingLog: StateFlow<List<String>> = _tradingLog.asStateFlow()

    init {
        observeLog()
    }

    private fun observeLog() {
        scope.launch(Dispatchers.IO) {

            val mutableList = ArrayList<String>()

            for (i in 0..99) {
                val event: String = "${getTime()} | Test event $i"
                mutableList.add(event)
                println("Add to log ${mutableList.size}: Thread ${Thread.currentThread().name}")

                _tradingLog.update { ArrayList(mutableList.reversed()) }

                Thread.sleep(500)

                println("list size ${mutableList.size}")
            }

        }


    }

    private fun getTime(): Long = Calendar.getInstance(Locale.getDefault()).timeInMillis
}