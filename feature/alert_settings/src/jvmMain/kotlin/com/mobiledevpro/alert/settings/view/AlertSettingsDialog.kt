package com.mobiledevpro.alert.settings.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobiledepro.main.domain.model.AlertTrigger
import com.mobiledevpro.alert.settings.view.state.AlertSettingsUIState
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.component.Dialog

@Composable
fun AlertSettingsDialog(
    modifier: Modifier = Modifier,
    state: AlertSettingsUIState,
    onChanged: (AlertTrigger) -> Unit,
    onClose: () -> Unit,
    onSave: () -> Unit
) {

    Dialog(modifier = modifier) {
        AlertSettingsBox(
            state = state,
            onChanged = onChanged,
            onClickClose = onClose,
            onClickSave = onSave
        )
    }
}

@Composable
@Preview
fun AlertSettingsDialogPreview() {
    Theme {
        AlertSettingsDialog(
            state = AlertSettingsUIState.Hidden,
            onClose = {},
            onSave = {},
            onChanged = {}
        )
    }
}