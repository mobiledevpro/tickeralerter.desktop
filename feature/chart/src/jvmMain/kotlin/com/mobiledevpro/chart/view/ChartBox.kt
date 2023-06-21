package com.mobiledevpro.chart.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
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

    val higherHighPrice = remember { mutableStateOf(0.0) }
    val lowerLowPrice = remember { mutableStateOf(0.0) }
    val yPixelStep = remember { mutableStateOf(0.0) }
    val xCandleWith = remember { mutableStateOf(0f) }

    WidgetBox(modifier = modifier) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            println("Chart Canvas")

            higherHighPrice.value = chart.getHigherHighPrice()
            lowerLowPrice.value = chart.getLowerLowPrice()

            val xSize = size.width
            val ySize = size.height

            //Find price movement for 1 px
            yPixelStep.value = higherHighPrice.value.minus(lowerLowPrice.value) / ySize

            //Find candle width
            xCandleWith.value = xSize / chart.candlesCount()

            drawXAxis()
            drawYAxis()
        }

        //Draw the chart
        Spacer(modifier = Modifier.fillMaxSize().drawWithCache {
            onDrawBehind {
                chart.getLimitedCandleList().forEachIndexed { index, candle ->
                    drawCandle(
                        width = xCandleWith.value - 1, //1px for space between candles
                        offsetX = (xCandleWith.value * index),
                        lowY = (higherHighPrice.value.minus(candle.priceLow) / yPixelStep.value).toFloat(),
                        highY = (higherHighPrice.value.minus(candle.priceHigh) / yPixelStep.value).toFloat(),
                        openY = (higherHighPrice.value.minus(candle.priceOpen) / yPixelStep.value).toFloat(),
                        closeY = (higherHighPrice.value.minus(candle.priceClose) / yPixelStep.value).toFloat(),
                        color = if (candle.priceOpen < candle.priceClose) candleGreen else candleRed
                    )
                }
            }
        })

        /*
        //Draw EMA
        Spacer(modifier = Modifier.fillMaxSize().drawWithCache {

            val emaPoints : List<Double> = chart.calcEMA(50)

            val path = Path()

            emaPoints.forEach {price ->
                val pointY

            }
            path.moveTo(0f, 0f)
            path.lineTo(size.width / 2f, size.height / 2f)
            path.lineTo(size.width, 0f)
            path.close()

            onDrawBehind {

            }
        })*/


        /*
                    chart.calcEMA(50)
                        .also {
                            drawEma(it)
                        }

         */


        /*
                    chart.calcEMA(200).forEach {
                        println("EMA 200: $it ")
                    }

         */

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

/*
fun DrawScope.drawEma(points: List<Double>) {
    Spacer(
        modifier = Modifier
            .drawWithCache {
                val path = Path()
                path.moveTo(0f, 0f)
                path.lineTo(size.width / 2f, size.height / 2f)
                path.lineTo(size.width, 0f)
                path.close()
                onDrawBehind {
                    drawPath(path, Color.Magenta, style = Stroke(width = 10f))
                }
            }
            .fillMaxSize()
    )

}

 */