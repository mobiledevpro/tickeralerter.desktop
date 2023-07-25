package com.mobiledevpro.chart.view.ext

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope


fun DrawScope.drawXAxis(chartSize: Size) {
    drawLine(
        color = Color.DarkGray,
        start = Offset(0f, chartSize.height), // values in pixels
        end = Offset(chartSize.width, chartSize.height),
        strokeWidth = 1f // values in pixels
    )
}

fun DrawScope.drawYAxis(chartSize: Size) {
    drawLine(
        color = Color.DarkGray,
        start = Offset(0f, 0f),
        end = Offset(0f, chartSize.height),
        strokeWidth = 1f
    )
}

fun DrawScope.drawCandle(
    width: Float,
    offsetX: Float,
    lowY: Float,
    highY: Float,
    openY: Float,
    closeY: Float,
    color: Color
) {
    //Candle shadow
    drawLine(
        color = color,
        start = Offset(offsetX + width / 2, lowY),
        end = Offset(offsetX + width / 2, highY),
        strokeWidth = 1f
    )

    //Candle body
    drawLine(
        color = color,
        start = Offset(offsetX + width / 2, openY),
        end = Offset(offsetX + width / 2, closeY),
        strokeWidth = width
    )
}