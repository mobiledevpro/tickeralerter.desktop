package com.mobiledevpro.alert.settings.view.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledevpro.ui.defaults.Defaults

@Composable
fun RowScope.TextLabel(text: String) {
    Text(
        text = text,
        color = Defaults.TextField.TextColorInactive,
        fontSize = Defaults.TextField.FontSize,
        modifier = Modifier.weight(0.3F)
            .padding(8.dp)
            .fillMaxWidth()
            .height(Defaults.TextFieldHeight)
    )
}