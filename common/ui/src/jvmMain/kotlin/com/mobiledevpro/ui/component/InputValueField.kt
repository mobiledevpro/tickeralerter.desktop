package com.mobiledevpro.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.defaults.Defaults

@Composable
fun InputValueField(
    modifier: Modifier,
    defaultValue: String = "",
    hint: String = "",
    type: InputValueType,
    onTextChanged: (String) -> Unit
) {

    var text by remember { mutableStateOf(defaultValue) }
    var trailingIconVisible by remember { mutableStateOf(text.isNotEmpty()) }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                trailingIconVisible = it.isNotEmpty()
                onTextChanged(it)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = when (type) {
                    InputValueType.PRICE -> KeyboardType.Decimal
                    else -> KeyboardType.Text
                }
            ),
            placeholder = { Text(text = hint, fontSize = Defaults.TextFieldFontSize) },
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
                if (trailingIconVisible)
                    IconButton(
                        onClick = {
                            text = ""
                            trailingIconVisible = false
                            onTextChanged(text)
                        },
                        modifier = Modifier.size(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onSurface.copy(alpha = 0.3F)
                        )
                    }
            },
            modifier = modifierMaxWidth.padding(
                top = 8.dp,
                start = 4.dp,
                end = 4.dp
            ).heightIn(Defaults.TextFieldHeight)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                    shape = Defaults.TextField.Shape
                )
        )
    }
}

enum class InputValueType {
    TEXT,
    PRICE
}