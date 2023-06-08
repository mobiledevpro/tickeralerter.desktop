package com.mobiledevpro.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobiledevpro.ui.accent

@Composable
fun TickerSearchBar(modifier: Modifier, onSearchChange: (String) -> Unit) {
    var searchText by rememberSaveable { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = {
            searchText = it
            onSearchChange(searchText)
        },
        placeholder = { Text("Search") },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f),
            fontSize = 14.sp
        ),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = MaterialTheme.colors.accent,
            backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
            placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        modifier = modifier
    )
}
