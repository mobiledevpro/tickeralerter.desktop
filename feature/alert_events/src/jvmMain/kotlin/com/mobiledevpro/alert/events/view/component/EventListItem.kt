package com.mobiledevpro.alert.events.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.AlertEvent
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.white

@Composable
fun EventListItem(item: AlertEvent) {
    //Text
    Column(
        modifier = modifierMaxWidth
    ) {

        Column(modifier = modifierMaxWidth.padding(vertical = 4.dp)) {
            Text(
                text = item.message,
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.white),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = item.getTimeStr(),
                style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Divider(thickness = 1.dp)
    }
}

