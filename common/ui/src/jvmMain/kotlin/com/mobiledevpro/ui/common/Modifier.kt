package com.mobiledevpro.ui.common

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp

val modifierMaxSize = Modifier.fillMaxSize()
val modifierMaxWidth = Modifier.fillMaxWidth()
val modifierMaxHeight = Modifier.fillMaxHeight()

val modifierListItem = Modifier.height(32.dp).fillMaxWidth()

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.onDoubleClick(unit: () -> Unit) =
    this.onPointerEvent(PointerEventType.Press) {
        when {
            it.buttons.isPrimaryPressed ->
                when ((it.nativeEvent as java.awt.event.MouseEvent).clickCount) {
                    2 -> unit()
                }
        }
    }