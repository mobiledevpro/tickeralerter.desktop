package com.mobiledevpro.alert.settings.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.mobiledepro.main.domain.model.AlertSettingsUIState
import com.mobiledepro.main.domain.model.AlertTrigger
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.component.Dialog

@Composable
fun AlertSettingsDialog(
    modifier: Modifier = Modifier,
    state: AlertSettingsUIState,
    onClose: () -> Unit,
    onSave: (AlertTrigger) -> Unit,
    onUpdate: (AlertTrigger) -> Unit,
    tickerList: List<Ticker>
) {
    //Find out an alert trigger to change
    val alertTrigger: AlertTrigger by remember {
        mutableStateOf(
            when (state) {
                is AlertSettingsUIState.Success -> state.trigger
                else -> (tickerList.find { it.selected }
                    ?: tickerList[0]).let { ticker -> AlertTrigger(symbol = ticker.symbol) }
            }
        )
    }

    Dialog(modifier = modifier) {
        AlertSettingsBox(
            trigger = alertTrigger,
            tickerList = tickerList,
            onUpdate = onUpdate,
            onClickClose = onClose,
            onClickSave = onSave
        )
    }
}

@Composable
@Preview
fun AlertSettingsDialogPreview() {
    Theme {
        AlertSettingsDialog(
            state = AlertSettingsUIState.Empty,
            onClose = {},
            onSave = {},
            onUpdate = {},
            tickerList = emptyList()
        )
    }
}