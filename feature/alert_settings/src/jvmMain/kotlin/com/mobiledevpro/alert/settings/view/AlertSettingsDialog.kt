package com.mobiledevpro.alert.settings.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.component.Dialog

@Composable
fun AlertSettingsDialog(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    onSave: () -> Unit,
    watchList: List<Ticker>
) {
    Dialog(modifier = modifier) {
        AlertSettingsBox(
            alertTrigger = null,
            onClickClose = onClose,
            onClickSave = onSave,
            watchList = watchList
        )
    }
}

@Composable
@Preview
fun AlertSettingsDialogPreview() {
    Theme {
        AlertSettingsDialog(
            onClose = {},
            onSave = {},
            watchList = emptyList()
        )
    }
}