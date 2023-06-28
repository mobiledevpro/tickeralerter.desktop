package com.mobiledevpro.chart.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.ChartSettings
import com.mobiledevpro.ui.component.WidgetBox

@Composable
fun ChartSettingsBox(
    settings: ChartSettings,
    onChangeSettings: (ChartSettings) -> Unit,
    modifier: Modifier = Modifier
) {

    WidgetBox(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Indicators",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()
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
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text, style = MaterialTheme.typography.button)

        Switch(
            checked = isChecked,
            onCheckedChange = onChanged
        )
    }
}