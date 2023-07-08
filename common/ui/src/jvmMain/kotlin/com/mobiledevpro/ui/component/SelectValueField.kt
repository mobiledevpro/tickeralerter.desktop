package com.mobiledevpro.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobiledevpro.ui.accent
import com.mobiledevpro.ui.defaults.Defaults

@Composable
fun SelectValueField(modifier: Modifier, value: String, hint: String = "") {
    val shape = RoundedCornerShape(Defaults.TextFieldCornerSize)
    TextField(
        value = value,
        readOnly = true,
        onValueChange = {

        },
        placeholder = { Text(hint) },
        singleLine = true,
        shape = shape,
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f),
            fontSize = Defaults.TextFieldFontSize
        ),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = MaterialTheme.colors.accent,
            backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
            placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
        },
        modifier = modifier.padding(4.dp).heightIn(Defaults.TextFieldHeight).border(
            width = 1.dp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
            shape = shape
        )
    )
}