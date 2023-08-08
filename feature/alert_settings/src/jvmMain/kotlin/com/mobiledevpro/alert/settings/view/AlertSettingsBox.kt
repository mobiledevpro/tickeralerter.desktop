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
import com.mobiledevpro.alert.settings.view.component.Header
import com.mobiledevpro.alert.settings.view.state.AlertSettingsUIState
import com.mobiledevpro.ui.component.WidgetBox

@Composable
fun AlertSettingsBox(
    modifier: Modifier = Modifier,
    state: AlertSettingsUIState,
    onClickSave: (AlertTrigger) -> Unit,
    onClickClose: () -> Unit,
) {

    if (state is AlertSettingsUIState.Hidden) return

    val trigger = (state as AlertSettingsUIState.Visible).trigger
    println("::SETTINGS: TRIGGER TIME CREATED ${trigger.timeCreated}")

    /*
    var selectedTriggerOption by remember { mutableStateOf(SimpleTab.ONLY_ONCE) }
    */
    val isEdit: Boolean = trigger.timeCreated?.let { it > 0 } ?: false
    var isButtonSaveEnabled by remember { mutableStateOf(true) }


    WidgetBox(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Header(edit = isEdit, onClose = onClickClose)

            /* ConditionRules(
                 tickerList = tickerList,
                 trigger = trigger,
                 onChange = { updatedTrigger ->
                     println("::TRIGGER CHANGED ${updatedTrigger.alertSettings.targetPrice}")
                     //check if price is 0, don't allow to save settings
                     isButtonSaveEnabled = updatedTrigger.saveEnabled()
                 }
             )

             Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

             TriggerRules(
                 selectedTrigger = selectedTriggerOption,
                 onSelectTrigger = {
                     selectedTriggerOption = it
                 }
             )*/

            Divider(thickness = 1.dp, modifier = Modifier.padding(top = 32.dp, bottom = 4.dp))

            ButtonSave(
                enabled = isButtonSaveEnabled,
                onClick = {
                    onClickSave(trigger)
                })
        }
    }

}