package com.mobiledevpro.feature.chart

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mobiledevpro.ui.component.WidgetBox

@Composable
fun ChartBox(modifier: Modifier = Modifier) {
    WidgetBox(modifier = modifier) {
        Text("Chart here", modifier = Modifier.align(Alignment.Center))
    }
}
