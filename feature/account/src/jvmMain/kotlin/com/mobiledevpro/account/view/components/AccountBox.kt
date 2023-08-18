package com.mobiledevpro.account.view.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.WalletBalance
import com.mobiledepro.main.domain.model.fakeBalances
import com.mobiledevpro.account.view.state.AccountUIState
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.common.modifierListItem
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.component.RowTitle
import com.mobiledevpro.ui.component.TextCaptionBox
import com.mobiledevpro.ui.component.WidgetBox

@Composable
fun AccountBox(
    modifier: Modifier,
    state: AccountUIState
) {

    var hideBalance by remember { mutableStateOf(true) }

    WidgetBox(modifier = modifier) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifierMaxWidth
            ) {
                Text(
                    text = "Wallet",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                IconButton(
                    onClick = {
                        hideBalance = !hideBalance
                    }
                ) {
                    Icon(
                        imageVector = if (hideBalance) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = null
                    )
                }
            }


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifierMaxWidth
            ) {
                RowTitle("Asset", textAlign = TextAlign.Start, modifier = Modifier.weight(1f))
                RowTitle("Margin Balance", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                RowTitle("Balance", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                RowTitle("Unrealized PnL", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            }

            Divider(thickness = 1.dp)


            when (state) {
                is AccountUIState.Success -> Success(
                    balances = state.balances,
                    hideBalance
                )

                is AccountUIState.Empty -> Empty()
                is AccountUIState.Loading -> Loading()
            }

        }
    }
}

@Composable
internal fun Success(balances: List<WalletBalance>, hideBalance: Boolean) {

    LazyColumn {
        items(
            items = balances,
            key = { it.listKey() }
        ) { balance ->
            WalletListItem(
                balance = balance,
                modifier = modifierListItem,
                hideBalance = hideBalance
            )
        }
    }
}

@Composable
internal fun Empty() {
    TextCaptionBox(
        text = "Balance is not gotten from the exchange.\nPlease check API Key restrictions."
    )
}

@Composable
internal fun Loading() {
    TextCaptionBox(
        text = "Loading..."
    )
}


@Preview
@Composable
fun AccountBoxPreview() {
    Theme {
        AccountBox(
            modifier = Modifier,
            state = AccountUIState.Success(fakeBalances)
        )
    }
}