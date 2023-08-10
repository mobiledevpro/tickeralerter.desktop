package com.mobiledevpro.alert.settings.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledevpro.ui.common.modifierMaxWidth

@Composable
fun Header(edit: Boolean, symbol: String, onClose: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifierMaxWidth
    ) {
        Text(
            text = if (edit) "Edit Alert for $symbol" else "Add Alert",
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
