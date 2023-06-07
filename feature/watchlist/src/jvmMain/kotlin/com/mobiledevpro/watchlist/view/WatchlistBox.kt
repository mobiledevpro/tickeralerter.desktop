package com.mobiledevpro.watchlist.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledepro.main.domain.model.fakeTickerListFirst
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.component.WidgetBox


@Composable
fun WatchlistBox(list: List<Ticker>, modifier: Modifier = Modifier, onClickAdd: () -> Unit) {
    println("Show Watchlist")
    WidgetBox(modifier = modifier) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Watchlist",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                IconButton(
                    onClick = onClickAdd
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }

            Divider(thickness = 1.dp)

            LazyColumn {
                items(list) { ticker ->
                    WatchlistItem(ticker)
                }
            }
        }
    }
}


@Preview
@Composable
fun WatchlistBoxPreview() {
    Theme {
        WatchlistBox(
            list = fakeTickerListFirst(),
            onClickAdd = {}
        )
    }
}