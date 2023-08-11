package com.mobiledevpro.alert.triggers.view.component

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
import com.mobiledepro.main.domain.model.AlertStatus
import com.mobiledepro.main.domain.model.AlertTrigger
import com.mobiledepro.main.domain.model.fakeAlertTriggersList
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.accent
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.common.onDoubleClick
import com.mobiledevpro.ui.component.PlayPauseIconButton
import com.mobiledevpro.ui.component.RemoveIconButton
import com.mobiledevpro.ui.component.SettingsIconButton
import com.mobiledevpro.ui.component.WidgetBox
import com.mobiledevpro.ui.orange
import com.mobiledevpro.ui.white

@Composable
fun TriggerListItem(
    item: AlertTrigger,
    onPause: () -> Unit,
    onStart: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    val textColor = if (item.status == AlertStatus.ACTIVE)
        MaterialTheme.colors.white
    else
        MaterialTheme.colors.onSurface.copy(alpha = 0.3f)

    val statusColor =
        if (item.status == AlertStatus.ACTIVE)
            MaterialTheme.colors.accent
        else
            MaterialTheme.colors.orange

    Column(
        modifier = modifierMaxWidth
            .onDoubleClick(onEdit)
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
                    text = when (item.status) {
                        AlertStatus.ACTIVE -> "Active"
                        AlertStatus.PAUSED -> "Paused"
                        AlertStatus.COMPLETED -> "Completed"
                    },
                    style = MaterialTheme.typography.caption.copy(color = statusColor)
                )
            }

            //Buttons
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                SettingsIconButton(
                    onClick = onEdit
                )

                PlayPauseIconButton(
                    active = item.status == AlertStatus.ACTIVE,
                    onClick = {
                        if (item.status == AlertStatus.ACTIVE)
                            onPause()
                        else
                            onStart()
                    }
                )
                RemoveIconButton(
                    onClick = onDelete
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
                onStart = {},
                onEdit = {},
                onDelete = {}
            )
        }
    }
}