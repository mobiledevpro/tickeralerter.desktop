package com.mobiledevpro.tickerlist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.tickerlist.view.state.TickerListUIState
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.component.TextCaptionBox
import com.mobiledevpro.ui.component.TickerSearchBar
import com.mobiledevpro.ui.component.WidgetBox


@Composable
internal fun TickerListBox(
    modifier: Modifier = Modifier,
    state: TickerListUIState,
    onClickAdd: (Ticker) -> Unit,
    onClickRemove: (Ticker) -> Unit,
    onClickClose: () -> Unit,
    onSearchChanged: (String) -> Unit
) {

    val modifierListItem by remember { mutableStateOf(modifierMaxWidth) }

    WidgetBox(modifier = modifier) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifierMaxWidth
            ) {
                Text(
                    text = "Add Ticker",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterVertically).padding(8.dp)
                )
                IconButton(
                    onClick = onClickClose
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            }

            Divider(thickness = 1.dp)

            TickerSearchBar(
                onSearchChange = onSearchChanged,
                modifier = modifierMaxWidth.padding(all = 8.dp)
            )

            when (state) {
                is TickerListUIState.Success ->
                    LazyColumn {
                        items(state.list) { ticker ->
                            TickerItem(
                                modifier = modifierListItem,
                                ticker = ticker,
                                onClickAdd = { onClickAdd(ticker) },
                                onClickRemove = { onClickRemove(ticker) }
                            )
                        }
                    }

                is TickerListUIState.Loading -> TextCaptionBox(text = "Loading...")
                is TickerListUIState.Empty -> TextCaptionBox(text = "No symbol found")
            }
        }
    }
}