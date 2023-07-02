package com.mobiledevpro.alert.settings.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.AlertEvent
import com.mobiledepro.main.domain.model.AlertTrigger
import com.mobiledepro.main.domain.model.fakeAlertTriggersList
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.component.SimpleTab
import com.mobiledevpro.ui.component.SimpleTabSwitcher
import com.mobiledevpro.ui.component.WidgetBox

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlertsBox(
    alertTriggerList: List<AlertTrigger>,
    alertEventList: List<AlertEvent>,
    modifier: Modifier = Modifier,
    onClickAdd: () -> Unit
) {
    val selectedTab = remember { mutableStateOf(SimpleTab.ALL) }

    WidgetBox(modifier = modifier) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Alerts",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                IconButton(
                    onClick = onClickAdd
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }

            SimpleTabSwitcher(
                tabs = listOf(SimpleTab.ALL, SimpleTab.LOG),
                selectedTab = selectedTab.value,
                onTabSelected = { tab ->
                    selectedTab.value = tab

                }
            )

            Divider(thickness = 1.dp, modifier = Modifier.padding(top = 4.dp))

            when (selectedTab.value) {
                SimpleTab.ALL -> TriggerList(alertTriggerList)
                SimpleTab.LOG -> EventList(alertEventList)
            }

        }

    }
}

@Composable
fun TriggerList(list: List<AlertTrigger>) {
    LazyColumn {
        items(list) { trigger ->
            TriggerListItem(
                item = trigger,
                onPause = {},
                onChange = {},
                onRemove = {}
            )
        }
    }
}

@Composable
fun EventList(list: List<AlertEvent>) {
    LazyColumn {
        items(list) { event ->
            EventListItem(
                item = event
            )
        }
    }
}


@Preview
@Composable
fun AlertsBoxPreview() {
    Theme {
        AlertsBox(
            alertTriggerList = fakeAlertTriggersList(),
            alertEventList = emptyList(),
            modifier = Modifier.fillMaxWidth(),
            onClickAdd = {}
        )
    }
}
