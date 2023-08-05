package com.mobiledevpro.orders.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledevpro.ui.common.modifierMaxSize
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.component.TextCaptionBox
import com.mobiledevpro.ui.component.WidgetBox

@Composable
fun OrdersBox(modifier: Modifier = Modifier) {
    WidgetBox(modifier = modifier) {

        Column(modifier = modifierMaxWidth) {
            Text(
                text = "Orders",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                modifier = modifierMaxWidth.padding(vertical = 4.dp)
            )
            Divider(thickness = 1.dp)

            Box(modifier = modifierMaxSize, contentAlignment = Alignment.Center) {
                TextCaptionBox(text = "Open/Close, Trading History. Coming soon...")
            }
        }

    }
}