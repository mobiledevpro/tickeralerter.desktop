package com.mobiledevpro.account.view.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.mobiledepro.main.domain.model.WalletBalance
import com.mobiledepro.main.domain.model.fakeBalances
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.white

@Composable
internal fun WalletListItem(modifier: Modifier = Modifier, balance: WalletBalance, hideBalance: Boolean) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        BalanceText(value = balance.asset, textAlign = TextAlign.Start)
        BalanceText(value = if (hideBalance) "**.**" else balance.marginBalance.toString())
        BalanceText(value = if (hideBalance) "**.**" else balance.balance.toString())
        BalanceText(value = if (hideBalance) "**.**" else balance.unrealizedPnL.toString())
    }
}


@Composable
fun RowScope.BalanceText(
    modifier: Modifier = Modifier.weight(1f),
    value: String,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colors.white
) {
    Text(
        text = value,
        style = MaterialTheme.typography.body2,
        textAlign = textAlign,
        color = color,
        modifier = modifier
    )
}


@Preview
@Composable
fun WalletListItemPreview() {
    Theme {
        WalletListItem(balance = fakeBalances[0], hideBalance = true)
    }
}