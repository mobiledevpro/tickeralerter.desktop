package com.mobiledevpro.alert.settings.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.AlertTrigger
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.alert.settings.view.component.ButtonSave
import com.mobiledevpro.alert.settings.view.component.ConditionRules
import com.mobiledevpro.alert.settings.view.component.Header
import com.mobiledevpro.alert.settings.view.component.TriggerRules
import com.mobiledevpro.ui.component.SimpleTab
import com.mobiledevpro.ui.component.WidgetBox

@Composable
fun AlertSettingsBox(
    modifier: Modifier = Modifier,
    trigger: AlertTrigger,
    tickerList: List<Ticker>,
    onUpdate: (AlertTrigger) -> Unit,
    onClickSave: (AlertTrigger) -> Unit,
    onClickClose: () -> Unit,
) {

    var selectedTriggerOption by remember { mutableStateOf(SimpleTab.ONLY_ONCE) }
    val editTrigger by remember { mutableStateOf(trigger) }
    val isEdit: Boolean = trigger.timeCreated?.let { true } ?: false


    WidgetBox(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Header(edit = isEdit, onClose = onClickClose)

            ConditionRules(
                tickerList = tickerList,
                trigger = editTrigger,
                onChange = onUpdate
            )

            Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            TriggerRules(
                selectedTrigger = selectedTriggerOption,
                onSelectTrigger = {
                    selectedTriggerOption = it
                }
            )

            Divider(thickness = 1.dp, modifier = Modifier.padding(top = 32.dp, bottom = 4.dp))

            ButtonSave(onClick = {
                onClickSave(trigger)
            })
        }
    }

}