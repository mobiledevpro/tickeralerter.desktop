package com.mobiledevpro.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.defaults.Defaults
import com.mobiledevpro.ui.white

@Composable
fun SelectValueField(
    modifier: Modifier,
    defaultValue: String? = null,
    valueList: List<String>,
    hint: String = "",
    onSelect: (value: String) -> Unit
) {

    val fieldHorizontalPadding = 4.dp

    var menuExpanded by remember { mutableStateOf(false) }
    var menuWidth by remember { mutableStateOf(0.dp) }
    var selectedValue by remember { mutableStateOf(defaultValue ?: valueList[0]) }

    val isMenuAvailable = valueList.size > 1

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        TextField(
            value = selectedValue,
            enabled = false,
            onValueChange = { },
            placeholder = { Text(hint) },
            singleLine = true,
            shape = Defaults.TextField.Shape,
            textStyle = LocalTextStyle.current.copy(
                color = Defaults.TextField.TextColor,
                fontSize = Defaults.TextFieldFontSize
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Defaults.TextField.CursorColor,
                backgroundColor = Defaults.TextField.BackgroundColor,
                placeholderColor = Defaults.TextField.PlaceholderColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            trailingIcon = {
                if (isMenuAvailable)
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            },
            modifier = modifierMaxWidth.padding(
                top = 8.dp,
                start = fieldHorizontalPadding,
                end = fieldHorizontalPadding
            ).heightIn(Defaults.TextFieldHeight)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                    shape = Defaults.TextField.Shape
                )
                .onGloballyPositioned {
                    menuWidth = it.size.width.dp
                }
                .let {
                    if (isMenuAvailable)
                        it.clickable(onClick = { menuExpanded = true })
                    else
                        it
                }
        )

        //TODO: maxHeight should be calculated dynamically based on dialog size
        DropdownMenu(
            modifier = Modifier.width(menuWidth).heightIn(max = 192.dp),
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
            offset = DpOffset(fieldHorizontalPadding, 0.dp),
            content = {
                valueList.forEach { value ->
                    DropdownMenuItem(
                        onClick = {
                            selectedValue = value
                            menuExpanded = false
                            onSelect(value)
                        },
                        enabled = value != selectedValue,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = value,
                            color = if (selectedValue == value) Defaults.TextField.TextColor else MaterialTheme.colors.white,
                            fontSize = Defaults.TextFieldFontSize
                        )
                    }
                }
            }
        )
    }
}