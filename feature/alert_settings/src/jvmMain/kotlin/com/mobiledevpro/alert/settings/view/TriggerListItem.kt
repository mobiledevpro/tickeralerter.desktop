package com.mobiledevpro.alert.settings.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.AlertTrigger
import com.mobiledepro.main.domain.model.fakeAlertTriggersList
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.accent
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.component.PlayPauseIconButton
import com.mobiledevpro.ui.component.RemoveIconButton
import com.mobiledevpro.ui.component.SettingsIconButton
import com.mobiledevpro.ui.component.WidgetBox
import com.mobiledevpro.ui.orange
import com.mobiledevpro.ui.white

@Composable
fun TriggerListItem(
    item: AlertTrigger,
    onPause: (Boolean) -> Unit,
    onChange: () -> Unit,
    onRemove: () -> Unit
) {

    val textColor = if (item.active)
        MaterialTheme.colors.white
    else
        MaterialTheme.colors.onSurface.copy(alpha = 0.3f)

    val statusColor =
        if (item.active)
            MaterialTheme.colors.accent
        else
            MaterialTheme.colors.orange

    Column(
        modifier = modifierMaxWidth
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifierMaxWidth.padding(vertical = 4.dp)
        ) {

            //Text
            Column(
                modifier = Modifier.width(280.dp)
            ) {

                Text(
                    text = item.title(),
                    style = MaterialTheme.typography.body2.copy(color = textColor),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = if (item.active) "Active" else "Paused",
                    style = MaterialTheme.typography.caption.copy(color = statusColor)
                )
            }

            //Buttons
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                SettingsIconButton(
                    onClick = onChange
                )

                PlayPauseIconButton(
                    active = item.active,
                    onClick = {
                        onPause(!item.active)
                    }
                )
                RemoveIconButton(
                    onClick = onRemove
                )
            }
        }

        Divider(thickness = 1.dp)
    }
}

@Preview
@Composable
fun TriggerListItemPreview() {
    Theme {
        WidgetBox(modifier = modifierMaxWidth) {
            TriggerListItem(
                item = fakeAlertTriggersList().get(0),
                onPause = {},
                onChange = {},
                onRemove = {}
            )
        }
    }
}