package com.mobiledevpro.tickerlist.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.ui.component.TickerSearchBar
import com.mobiledevpro.ui.component.WidgetBox


@Composable
internal fun TickerListBox(
    list: List<Ticker>,
    modifier: Modifier = Modifier,
    onClickAdd: (Ticker) -> Unit,
    onClickRemove: (Ticker) -> Unit,
    onClickClose: () -> Unit,
    onSearchChanged: (String) -> Unit
) {

    WidgetBox(modifier = modifier) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
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
                modifier = Modifier.fillMaxWidth().padding(all = 8.dp)
            )

            LazyColumn {
                items(list) { ticker ->
                    TickerItem(
                        ticker = ticker,
                        onClickAdd = { onClickAdd(ticker) },
                        onClickRemove = { onClickRemove(ticker) }
                    )
                }
            }
        }
    }
}