package com.mobiledevpro.alert.settings.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.AlertFrequency
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.component.SimpleTab
import com.mobiledevpro.ui.component.SimpleTabSwitcher
import com.mobiledevpro.ui.defaults.Defaults

@Composable
internal fun FrequencyRules(frequency: AlertFrequency, onSelectFrequency: (AlertFrequency) -> Unit) {
    val selectedTab = when (frequency) {
        AlertFrequency.ONLY_ONCE -> SimpleTab.ONLY_ONCE
        AlertFrequency.EVERY_TIME -> SimpleTab.EVERY_TIME
    }

    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        TextLabel("Trigger")
        Column(modifier = modifierMaxWidth.weight(0.7f)) {
            SimpleTabSwitcher(
                modifier = modifierMaxWidth.padding(4.dp),
                tabs = listOf(SimpleTab.ONLY_ONCE, SimpleTab.EVERY_TIME),
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    when (tab) {
                        SimpleTab.ONLY_ONCE -> AlertFrequency.ONLY_ONCE
                        else -> AlertFrequency.EVERY_TIME
                    }.also(onSelectFrequency)
                }
            )

            Text(
                text = when (selectedTab) {
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