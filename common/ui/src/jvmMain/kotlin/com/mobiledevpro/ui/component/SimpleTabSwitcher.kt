package com.mobiledevpro.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobiledevpro.ui.accent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SimpleTabSwitcher(tabs: List<SimpleTab>, selectedTab: SimpleTab, onTabSelected: (SimpleTab) -> Unit) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f))
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            tabs.forEach { tab ->
                SimpleTabBox(
                    text = tab.name.lowercase().replaceFirstChar { it.uppercase() },
                    selected = tab == selectedTab,
                    modifier = Modifier
                        .weight(1f)
                        .onClick {
                            onTabSelected(tab)
                        }
                )
            }
        }
    }
}

@Composable
internal fun SimpleTabBox(text: String, selected: Boolean, modifier: Modifier) {
    val backColor: Color = if (selected)
        MaterialTheme.colors.accent.copy(alpha = 0.2f)
    else
        Color.Transparent

    val textColor: Color = MaterialTheme.colors.onSurface.copy(alpha = if (selected) 0.8f else 0.5f)

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = backColor)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.caption,
            color = textColor
        )
    }
}


enum class SimpleTab {
    ALL,
    LOG
}