package com.mobiledevpro.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledevpro.ui.backgroundTransparent

@Composable
fun Dialog(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.backgroundTransparent
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = modifier
                    .align(Alignment.Center)
                    .padding(32.dp)
                    .widthIn(min = 500.dp, max = 600.dp),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}