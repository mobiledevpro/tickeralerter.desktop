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

@Composable
fun TickerListDialog(
    modifier: Modifier = Modifier,
    state: TickerListUIState,
    onAdd: (Ticker) -> Unit,
    onRemove: (Ticker) -> Unit,
    onClose: () -> Unit,
    onSearch: (String) -> Unit
) {

    Dialog(modifier = modifier) {
        TickerListBox(
            state = state,
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
            state = TickerListUIState.Success(fakeTickerListFirst()),
            onAdd = {},
            onRemove = {},
            onClose = {},
            onSearch = {}
        )
    }
}