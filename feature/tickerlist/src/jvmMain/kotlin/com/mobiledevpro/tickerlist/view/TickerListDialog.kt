package com.mobiledevpro.tickerlist.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledepro.main.domain.model.fakeTickerListFirst
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.common.modifierMaxHeight
import com.mobiledevpro.ui.component.Dialog

@Composable
fun TickerListDialog(
    modifier: Modifier = Modifier,
    list: List<Ticker>,
    onAdd: (Ticker) -> Unit,
    onRemove: (Ticker) -> Unit,
    onClose: () -> Unit,
    onSearch: (String) -> Unit
) {

    Dialog(modifier = modifier) {
        TickerListBox(
            list = list,
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
            list = fakeTickerListFirst(),
            onAdd = {},
            onRemove = {},
            onClose = {},
            onSearch = {}
        )
    }
}