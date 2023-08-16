package com.mobiledevpro.account.view.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.mobiledepro.main.domain.model.WalletBalance
import com.mobiledepro.main.domain.model.fakeBalances
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.white

@Composable
internal fun WalletListItem(modifier: Modifier = Modifier, balance: WalletBalance) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        BalanceText(value = balance.asset)
        BalanceText(value = balance.marginBalance.toString())
        BalanceText(value = balance.balance.toString())
        BalanceText(value = balance.unrealizedPnL.toString())
    }
}


@Composable
fun BalanceText(
    modifier: Modifier = Modifier,
    value: String,
    textAlign: TextAlign = TextAlign.End,
    color: Color = MaterialTheme.colors.white
) {
    Text(
        text = value,
        style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.SemiBold),
        textAlign = textAlign,
        color = color,
        modifier = modifier
    )
}


@Preview
@Composable
fun WalletListItemPreview() {
    Theme {
        WalletListItem(balance = fakeBalances[0])
    }
}