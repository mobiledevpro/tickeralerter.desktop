package com.mobiledevpro.ui.defaults

import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobiledevpro.ui.accent
import com.mobiledevpro.ui.white

object Defaults {
    val TextFieldHeight = 48.dp

    val TextFieldCornerSize = 16.dp

    val TextFieldFontSize = 14.sp

    val ButtonColors: @Composable (isSelected: Boolean) -> ButtonColors = { isSelected ->
        ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colors.white,
            backgroundColor = if (isSelected)
                MaterialTheme.colors.surface
            else
                MaterialTheme.colors.accent
        )

    }

    object Button {
        object Large {
            val Width = 144.dp
        }
    }
}