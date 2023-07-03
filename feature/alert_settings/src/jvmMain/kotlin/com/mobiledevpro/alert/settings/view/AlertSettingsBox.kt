package com.mobiledevpro.alert.settings.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.AlertTrigger
import com.mobiledevpro.ui.component.WidgetBox

@Composable
internal fun AlertSettingsBox(modifier: Modifier = Modifier, alertTrigger: AlertTrigger?, onClickClose: () -> Unit) {

    val isEdit: Boolean = alertTrigger?.let { true } ?: false

    WidgetBox(modifier = modifier) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isEdit) "Edit Alert" else "Add Alert",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterVertically).padding(8.dp)
                )
                IconButton(
                    onClick = onClickClose
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            }

            Divider(thickness = 1.dp)


            //TODO: Fields for Alert settings
        }
    }

}