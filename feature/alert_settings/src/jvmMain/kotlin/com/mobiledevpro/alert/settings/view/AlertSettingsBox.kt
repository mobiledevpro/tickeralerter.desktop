package com.mobiledevpro.alert.settings.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.AlertTrigger
import com.mobiledevpro.alert.settings.view.component.ButtonSave
import com.mobiledevpro.alert.settings.view.component.ConditionRules
import com.mobiledevpro.alert.settings.view.component.Header
import com.mobiledevpro.alert.settings.view.component.TriggerRules
import com.mobiledevpro.alert.settings.view.state.AlertSettingsUIState
import com.mobiledevpro.ui.component.SimpleTab
import com.mobiledevpro.ui.component.WidgetBox

@Composable
fun AlertSettingsBox(
    modifier: Modifier = Modifier,
    state: AlertSettingsUIState,
    onChanged: (AlertTrigger) -> Unit,
    onClickSave: () -> Unit,
    onClickClose: () -> Unit,
) {

    val trigger = when (state) {
        is AlertSettingsUIState.Visible -> state.trigger
        else -> return
    }

    val tickerList = state.tickerList

    var selectedTriggerOption by remember { mutableStateOf(SimpleTab.ONLY_ONCE) }
    val isEdit: Boolean = trigger.timeCreated?.let { it > 0 } ?: false
    var isButtonSaveEnabled by remember { mutableStateOf(true) }


    WidgetBox(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Header(edit = isEdit, symbol = trigger.symbol, onClose = onClickClose)

            ConditionRules(
                tickerList = tickerList,
                trigger = trigger,
                onChange = { updatedTrigger ->
                    //check if price is 0, don't allow to save settings
                    isButtonSaveEnabled = updatedTrigger.saveEnabled()
                    onChanged(updatedTrigger)
                }
            )


            Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            TriggerRules(
                selectedTrigger = selectedTriggerOption,
                onSelectTrigger = {
                    selectedTriggerOption = it
                }
            )

            Divider(thickness = 1.dp, modifier = Modifier.padding(top = 32.dp, bottom = 4.dp))

            ButtonSave(
                enabled = isButtonSaveEnabled,
                onClick = onClickSave
            )
        }
    }

}