package com.mobiledevpro.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WidgetBox(
    modifier: Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface,
    content: @Composable BoxScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = backgroundColor,
        modifier = modifier.padding(16.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}