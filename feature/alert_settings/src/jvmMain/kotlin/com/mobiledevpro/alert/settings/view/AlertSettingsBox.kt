package com.mobiledevpro.alert.settings.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.AlertTrigger
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.component.SelectValueField
import com.mobiledevpro.ui.component.SimpleTab
import com.mobiledevpro.ui.component.SimpleTabSwitcher
import com.mobiledevpro.ui.component.WidgetBox
import com.mobiledevpro.ui.defaults.Defaults

@Composable
internal fun AlertSettingsBox(
    modifier: Modifier = Modifier,
    alertTrigger: AlertTrigger?,
    onClickClose: () -> Unit,
    onClickSave: () -> Unit,
    watchList: List<Ticker>
) {

    var selectedTrigger by remember { mutableStateOf(SimpleTab.ONLY_ONCE) }
    val isEdit: Boolean = alertTrigger?.let { true } ?: false

    WidgetBox(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Header(edit = isEdit, onClose = onClickClose)
            ConditionRules(tickerList = watchList)
            Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            TriggerRules(
                selectedTrigger = selectedTrigger,
                onSelectTrigger = {
                    selectedTrigger = it
                }
            )
            Divider(thickness = 1.dp, modifier = Modifier.padding(top = 32.dp, bottom = 4.dp))

            Button(
                modifier = Modifier.width(Defaults.Button.Large.Width),
                onClick = onClickSave,
                colors = Defaults.ButtonColors(false)
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.button
                )
            }
        }
    }

}


@Composable
fun Header(edit: Boolean, onClose: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifierMaxWidth
    ) {
        Text(
            text = if (edit) "Edit Alert" else "Add Alert",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.align(Alignment.CenterVertically).padding(8.dp)
        )
        IconButton(
            onClick = onClose
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
    }

    Divider(thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))
}

@Composable
fun ConditionRules(tickerList: List<Ticker>) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        TextLabel("Condition")

        Column(modifier = modifierMaxWidth.weight(0.7f)) {
            SelectValueField(
                modifier = modifierMaxWidth,
                valueList = tickerList.mapTo(ArrayList<String>()) { it.symbol },
                onSelect = {})

            SelectValueField(modifier = modifierMaxWidth, listOf("Crossing"), onSelect = {})

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifierMaxWidth) {
                SelectValueField(modifier = modifierMaxWidth.weight(1f), listOf("Price"), onSelect = {})
                SelectValueField(modifier = modifierMaxWidth.weight(1f), listOf("31,000"), onSelect = {})
            }
        }

    }
}

@Composable
fun TriggerRules(selectedTrigger: SimpleTab, onSelectTrigger: (SimpleTab) -> Unit) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        TextLabel("Trigger")
        Column(modifier = modifierMaxWidth.weight(0.7f)) {
            SimpleTabSwitcher(
                modifier = modifierMaxWidth.padding(4.dp),
                tabs = listOf(SimpleTab.ONLY_ONCE, SimpleTab.EVERY_TIME),
                selectedTab = selectedTrigger,
                onTabSelected = onSelectTrigger
            )

            Text(
                text = when (selectedTrigger) {
                    SimpleTab.ONLY_ONCE -> "The alert will only trigger once"
                    SimpleTab.EVERY_TIME -> "The alert will trigger every time the condition is met"
                    else -> ""
                },
                fontSize = Defaults.TextFieldFontSize,
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)),
                minLines = 2,
                maxLines = 2,
                modifier = modifierMaxWidth.padding(8.dp)
            )
        }

    }
}

@Composable
fun RowScope.TextLabel(text: String) {
    Text(
        text = text,
        modifier = Modifier.weight(0.3F)
            .padding(8.dp)
            .fillMaxWidth()
            .height(Defaults.TextFieldHeight),
    )
}

@Composable
@Preview
fun AlertSettingsBoxPreview() {
    Theme {
        AlertSettingsDialog(
            onClose = {},
            onSave = {},
            watchList = emptyList()
        )
    }
}