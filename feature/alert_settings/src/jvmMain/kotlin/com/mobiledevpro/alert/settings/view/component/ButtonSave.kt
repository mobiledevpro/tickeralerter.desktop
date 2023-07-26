package com.mobiledevpro.alert.settings.view.component

import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobiledevpro.ui.defaults.Defaults

@Composable
internal fun ButtonSave(enabled: Boolean, onClick: () -> Unit) {
    Button(
        modifier = Modifier.width(Defaults.Button.Large.Width),
        onClick = onClick,
        colors = Defaults.ButtonColors(false),
        shape = Defaults.Button.Large.Shape,
        enabled = enabled
    ) {
        Text(
            text = "Save",
            style = MaterialTheme.typography.button
        )
    }
}