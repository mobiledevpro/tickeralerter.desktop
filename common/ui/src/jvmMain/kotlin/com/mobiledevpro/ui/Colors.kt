package com.mobiledevpro.ui

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@get:Composable
val Colors.lightGreen: Color
    get() = Color(0x9932CD32)

@get:Composable
val Colors.red: Color
    get() = Color(0x99F44336)

@get:Composable
val Colors.white: Color
    get() = Color(0x99FFFFFF)


@get:Composable
val Colors.accent: Color
    get() = Color(0x9903DAC6)


@get:Composable
val Colors.backgroundTransparent: Color
    get() = Color(0xFF121212).copy(alpha = 0.7F)