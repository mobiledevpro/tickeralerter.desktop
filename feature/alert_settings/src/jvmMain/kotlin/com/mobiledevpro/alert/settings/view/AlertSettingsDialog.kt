package com.mobiledevpro.alert.settings.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.component.Dialog

@Composable
fun AlertSettingsDialog(
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    Dialog(modifier = modifier) {
        AlertSettingsBox(
            modifier = Modifier.fillMaxHeight(),
            alertTrigger = null,
            onClickClose = onClose
        )
    }
}

@Composable
@Preview
fun AlertSettingsDialogPreview() {
    Theme {
        AlertSettingsDialog(
            onClose = {}
        )
    }
}