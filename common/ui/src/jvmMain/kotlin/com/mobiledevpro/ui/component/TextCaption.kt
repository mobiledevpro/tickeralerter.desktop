package com.mobiledevpro.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.white

@Composable
fun TextCaptionBox(text: String, icon: ImageVector? = null) {
    Box(
        modifier = modifierMaxWidth,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            icon?.also {
                Icon(imageVector = icon, contentDescription = null, modifier = Modifier.padding(16.dp))
            }
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.white),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}