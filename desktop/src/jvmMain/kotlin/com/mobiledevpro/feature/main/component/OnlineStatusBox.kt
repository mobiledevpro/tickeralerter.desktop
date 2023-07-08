package com.mobiledevpro.feature.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobiledevpro.common.util.timeToString
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.positiveCandleColor
import com.mobiledevpro.ui.red

@Composable
fun OnlineStatus(modifier: Modifier, serverTime: Long) {
    /*Online status*/
    Box(
        modifier = modifier
            .background(
                color = if (serverTime > 0)
                    MaterialTheme.colors.positiveCandleColor.copy(alpha = 0.5f)
                else
                    MaterialTheme.colors.red
            )
    ) {
        Text(
            text = if (serverTime > 0) "Online" else "Offline",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            modifier = modifierMaxWidth
        )

        if (serverTime > 0)
            Text(
                text = "Server time: ${serverTime.timeToString()}",
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.End,
                modifier = modifierMaxWidth.padding(horizontal = 16.dp)
            )
    }
}