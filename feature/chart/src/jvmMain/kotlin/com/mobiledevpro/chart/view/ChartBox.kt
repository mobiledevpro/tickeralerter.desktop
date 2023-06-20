package com.mobiledevpro.chart.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.mobiledepro.main.domain.model.Chart
import com.mobiledevpro.ui.candleGreen
import com.mobiledevpro.ui.candleRed
import com.mobiledevpro.ui.component.WidgetBox

@Composable
fun ChartBox(chart: Chart, modifier: Modifier = Modifier) {

    val candleRed: Color = MaterialTheme.colors.candleRed
    val candleGreen: Color = MaterialTheme.colors.candleGreen

    WidgetBox(modifier = modifier) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            println("Chart Canvas")
            val higherHighPrice = chart.getHigherHighPrice()
            val lowerLowPrice = chart.getLowerLowPrice()

            val xSize = size.width
            val ySize = size.height

            //Find price movement for 1 px
            val yPixelStep: Double = higherHighPrice.minus(lowerLowPrice) / ySize
            //Find candle width
            val xCandleWith: Float = xSize / chart.candlesCount()

            drawXAxis()
            drawYAxis()

            chart.candleList.forEachIndexed { index, candle ->
                drawCandle(
                    width = xCandleWith - 1, //1px for space between candles
                    offsetX = (xCandleWith * index),
                    lowY = (higherHighPrice.minus(candle.priceLow) / yPixelStep).toFloat(),
                    highY = (higherHighPrice.minus(candle.priceHigh) / yPixelStep).toFloat(),
                    openY = (higherHighPrice.minus(candle.priceOpen) / yPixelStep).toFloat(),
                    closeY = (higherHighPrice.minus(candle.priceClose) / yPixelStep).toFloat(),
                    color = if (candle.priceOpen < candle.priceClose) candleGreen else candleRed
                )
            }
        }

    }
}

fun DrawScope.drawXAxis() {
    drawLine(
        color = Color.DarkGray,
        start = Offset(0f, size.height), // values in pixels
        end = Offset(size.width, size.height),
        strokeWidth = 1f // values in pixels
    )
}

fun DrawScope.drawYAxis() {
    drawLine(
        color = Color.DarkGray,
        start = Offset(0f, 0f),
        end = Offset(0f, size.height),
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
