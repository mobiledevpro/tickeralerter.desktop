package com.mobiledevpro.alert.settings.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobiledepro.main.domain.model.AlertSettings
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.component.Dialog

@Composable
fun AlertSettingsDialog(
    modifier: Modifier = Modifier,
    alertCondition: AlertSettings,
    onClose: () -> Unit,
    onSave: () -> Unit,
    onUpdate: (AlertSettings) -> Unit,
    watchList: List<Ticker>
) {
    Dialog(modifier = modifier) {
        AlertSettingsBox(
            alertTrigger = null,
            alertCondition = alertCondition,
            onUpdate = onUpdate,
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
            alertCondition = AlertSettings("BTCUSDT"),
            onClose = {},
            onSave = {},
            onUpdate = {},
            watchList = emptyList()
        )
    }
}