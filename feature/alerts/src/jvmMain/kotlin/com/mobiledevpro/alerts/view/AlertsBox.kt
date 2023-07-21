package com.mobiledevpro.alerts.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.AlertEvent
import com.mobiledepro.main.domain.model.AlertTrigger
import com.mobiledepro.main.domain.model.fakeAlertTriggersList
import com.mobiledevpro.alert.events.view.component.EventListItem
import com.mobiledevpro.alert.triggers.view.component.TriggerListItem
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.ui.common.modifierMaxSize
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.component.SimpleTab
import com.mobiledevpro.ui.component.SimpleTabSwitcher
import com.mobiledevpro.ui.component.WidgetBox
import com.mobiledevpro.ui.white

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlertsBox(
    alertTriggerList: List<AlertTrigger>,
    alertEventList: List<AlertEvent>,
    modifier: Modifier = Modifier,
    onClickAdd: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(SimpleTab.ALL) }

    WidgetBox(modifier = modifier) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifierMaxWidth
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
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                }
            )

            Divider(thickness = 1.dp, modifier = Modifier.padding(top = 4.dp))

            when (selectedTab) {
                SimpleTab.ALL ->
                    if (alertTriggerList.isEmpty())
                        NoTriggersBox()
                    else
                        TriggerList(alertTriggerList)

                SimpleTab.LOG ->
                    if (alertEventList.isEmpty())
                        NoEventsBox()
                    else
                        EventList(alertEventList)

                else -> {}
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

@Composable
fun NoEventsBox() {
    Box(modifier = modifierMaxSize, contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = Icons.Default.Alarm, contentDescription = null, modifier = Modifier.padding(16.dp))
            Text(
                text = "No alerts triggered yet!\nYou will see a list here when they do.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.white),
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}

@Composable
fun NoTriggersBox() {
    Box(modifier = modifierMaxSize, contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = Icons.Default.AddAlert, contentDescription = null, modifier = Modifier.padding(16.dp))
            Text(
                text = "Alerts are immediate notifications when conditions are met. Please use \"plus\" icon to create alert",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.white),
                modifier = Modifier.padding(16.dp)
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
            modifier = modifierMaxWidth,
            onClickAdd = {}
        )
    }
}
