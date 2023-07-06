package com.mobiledevpro.chart.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.ChartSettings
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.component.WidgetBox
import com.mobiledevpro.ui.white

@Composable
fun ChartSettingsBox(
    modifier: Modifier = Modifier,
    settings: ChartSettings,
    onChangeSettings: (ChartSettings) -> Unit
) {

    WidgetBox(modifier = modifier) {
        Column(modifier = modifierMaxWidth) {
            Text(
                text = "Indicators",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                modifier = modifierMaxWidth.padding(vertical = 4.dp)
            )
            Divider(thickness = 1.dp)

            IndicatorSwitcher(
                text = "EMA 50",
                isChecked = settings.ema50,
                onChanged = { isOn ->
                    settings.apply { ema50 = isOn }.also(onChangeSettings)
                }
            )

            IndicatorSwitcher(
                text = "EMA 200",
                isChecked = settings.ema200,
                onChanged = { isOn ->
                    settings.apply { ema200 = isOn }.also(onChangeSettings)
                }
            )

            IndicatorSwitcher(
                text = "EMA Ribbon",
                isChecked = settings.emaRibbon,
                onChanged = { isOn ->
                    settings.apply { emaRibbon = isOn }.also(onChangeSettings)
                }
            )
        }

    }
}

@Composable
fun IndicatorSwitcher(text: String, isChecked: Boolean, onChanged: (Boolean) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifierMaxWidth
    ) {
        Text(text = text, style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.white))

        Switch(
            checked = isChecked,
            onCheckedChange = onChanged
        )
    }
}