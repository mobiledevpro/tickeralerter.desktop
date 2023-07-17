package com.mobiledevpro.watchlist.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledepro.main.domain.model.fakeTickerListFirst
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.common.modifierMaxSize
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.component.WidgetBox
import com.mobiledevpro.ui.white
import com.mobiledevpro.watchlist.view.model.WatchlistUIState


@Composable
fun WatchlistBox(
    state: WatchlistUIState,
    modifier: Modifier = Modifier,
    onClickAdd: () -> Unit,
    onClickRemove: (Ticker) -> Unit,
    onSelect: (Ticker) -> Unit
) {

    println("::WatchList state: $state")

    WidgetBox(modifier = modifier) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifierMaxWidth
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

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifierMaxWidth
            ) {
                RowTitle("Symbol", modifier = Modifier.width(100.dp))
                RowTitle("Last", textAlign = TextAlign.End, modifier = Modifier.width(80.dp))
                RowTitle("24h Chg", textAlign = TextAlign.End, modifier = Modifier.width(80.dp))
                RowTitle("Chg %", textAlign = TextAlign.End, modifier = Modifier.width(80.dp))
                RowTitle("", modifier = Modifier.width(80.dp))
            }

            Divider(thickness = 1.dp)

            when (state) {
                is WatchlistUIState.Success -> Success(
                    list = state.list,
                    onClickRemove = onClickRemove,
                    onSelect = onSelect
                )

                is WatchlistUIState.Empty -> Empty()
            }

        }
    }
}

@Composable
fun Success(
    list: List<Ticker>,
    onClickRemove: (Ticker) -> Unit,
    onSelect: (Ticker) -> Unit
) {
    println("::WatchList success: ${list.size}")
    val modifierListItem = Modifier.height(32.dp)

    LazyColumn {
        items(list) { ticker ->
            WatchlistItem(
                modifier = modifierListItem,
                ticker = ticker,
                onRemove = { onClickRemove(ticker) },
                onSelect = { onSelect(ticker) }
            )
        }
    }
}

@Composable
fun Empty() {
    Box(
        modifier = modifierMaxSize,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No symbols in watchlist.\nYou can add it by clicking 'plus' icon above.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.white),
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}

@Composable
fun RowTitle(text: String, textAlign: TextAlign = TextAlign.Start, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.caption,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
        textAlign = textAlign,
        modifier = modifier.padding(vertical = 4.dp)
    )
}


@Preview
@Composable
fun WatchlistBoxPreview() {
    Theme {
        WatchlistBox(
            state = WatchlistUIState.Success(fakeTickerListFirst()),
            onClickAdd = {},
            onClickRemove = {},
            onSelect = {}
        )
    }
}