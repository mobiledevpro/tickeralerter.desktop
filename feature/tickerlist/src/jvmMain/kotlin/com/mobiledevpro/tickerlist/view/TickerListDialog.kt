package com.mobiledevpro.tickerlist.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledepro.main.domain.model.fakeTickerListFirst
import com.mobiledevpro.tickerlist.view.state.TickerListUIState
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.common.modifierMaxHeight
import com.mobiledevpro.ui.component.Dialog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TickerListDialog(
    modifier: Modifier = Modifier,
    uiState: StateFlow<TickerListUIState>,
    onAdd: (Ticker) -> Unit,
    onRemove: (Ticker) -> Unit,
    onClose: () -> Unit,
    onSearch: (String) -> Unit
) {

    Dialog(modifier = modifier) {
        TickerListBox(
            uiState = uiState,
            onClickAdd = onAdd,
            onClickRemove = onRemove,
            onClickClose = onClose,
            onSearchChanged = onSearch,
            modifier = modifierMaxHeight
        )
    }
}

@Preview
@Composable
fun TickerListDialogPreview() {
    Theme {
        TickerListDialog(
            uiState = MutableStateFlow(TickerListUIState.Success(fakeTickerListFirst())),
            onAdd = {},
            onRemove = {},
            onClose = {},
            onSearch = {}
        )
    }
}