package com.mobiledevpro.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobiledevpro.ui.common.modifierMaxSize
import com.mobiledevpro.ui.white

@Composable
fun TextCaptionBox(text: String) {
    Box(
        modifier = modifierMaxSize,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.white),
            modifier = Modifier.padding(16.dp)
        )
    }
}