package com.mobiledevpro.tickerlist.view

import androidx.compose.desktop.ui.tooling.preview.Preview
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
import com.mobiledepro.main.domain.model.fakeTickerListFirst
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.backgroundTransparent
import com.mobiledevpro.ui.component.WidgetBox

@Composable
fun TickerListSurface(
    list: List<Ticker>,
    onAdd: (Ticker) -> Unit,
    onRemove: (Ticker) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.backgroundTransparent
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            TickersBox(
                list = list,
                onClickAdd = onAdd,
                onClickRemove = onRemove,
                onClickClose = onClose,
                modifier = Modifier.padding(32.dp)
                    .fillMaxHeight()
                    .widthIn(min = 500.dp, max = 600.dp)
                    .align(Alignment.Center)
            )

        }
    }
}

@Composable
fun TickersBox(
    list: List<Ticker>,
    modifier: Modifier = Modifier,
    onClickAdd: (Ticker) -> Unit,
    onClickRemove: (Ticker) -> Unit,
    onClickClose: () -> Unit
) {
    println("Show Ticker list")
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


@Preview
@Composable
fun TickerItemPreview() {
    Theme {
        TickerListSurface(
            list = fakeTickerListFirst(),
            onAdd = {},
            onRemove = {},
            onClose = {}
        )
    }
}