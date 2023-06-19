package com.mobiledevpro.chart.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.mobiledepro.main.domain.model.Chart
import com.mobiledevpro.ui.candleGreen
import com.mobiledevpro.ui.candleRed
import com.mobiledevpro.ui.component.WidgetBox
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun ChartBox(chart: Chart, modifier: Modifier = Modifier) {

    val higherHighPrice = remember { mutableStateOf<Double>(0.0) }
    val lowerLowPrice = remember { mutableStateOf<Double>(0.0) }

    higherHighPrice.value = chart.getHigherHighPrice()
    lowerLowPrice.value = chart.getLowerLowPrice()

    println("Higher high ${chart.getHigherHighPrice()}")
    println("Lower low ${chart.getLowerLowPrice()}")

    val candleRed: Color = MaterialTheme.colors.candleRed
    val candleGreen: Color = MaterialTheme.colors.candleGreen

    WidgetBox(modifier = modifier) {
        //  Text("Chart here: candle list ${candleList.size}", modifier = Modifier.align(Alignment.Center))

        Canvas(modifier = Modifier.fillMaxSize()) {
            println("Chart Canvas")
            // your drawing code goes here
            val xSize = size.width
            val ySize = size.height

            //Find price movement for 1 px
            val yPixelStep: Double = higherHighPrice.value.minus(lowerLowPrice.value) / ySize
            //Find candle width
            val xCandleWith: Float = xSize / chart.candlesCount()



            drawXAxis()
            drawYAxis()

            chart.candleList.forEachIndexed { index, candle ->
                drawCandle(
                    width = xCandleWith - 1, //1px for space between candles
                    offsetX = (xCandleWith * index),
                    lowY = (higherHighPrice.value.minus(candle.priceLow) / yPixelStep).toFloat(),
                    highY = (higherHighPrice.value.minus(candle.priceHigh) / yPixelStep).toFloat(),
                    openY = (higherHighPrice.value.minus(candle.priceOpen) / yPixelStep).toFloat(),
                    closeY = (higherHighPrice.value.minus(candle.priceClose) / yPixelStep).toFloat(),
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


fun DrawScope.testDrawing() {
    /*
            drawRect(
                color = Color.Yellow,
                topLeft = Offset(50f, 50f), // values in pixels
                size = Size(100f, 100f) // values in pixels
            )

            drawCircle(
                color = Color.Red,
                radius = 100f, // values in pixels
                center = center
            )

 */





    drawLine(
        color = Color.DarkGray,
        start = Offset(0f, size.height), // values in pixels
        end = Offset(size.width, 0f),
        strokeWidth = 1f // values in pixels
    )
    /*
                val ploXLength = size.width / 100
                val plotYLength = size.height / 100

                val path = Path()
                path.moveTo(0f, size.height)
                for (i in 0..99) {

                }
                path.lineTo(size.width / 2f, size.height / 2f)
                path.lineTo(size.width, 0f)
                path.close()

                drawPath(

                )
    */

    val middleW = size.width / 2
    val middleH = size.height / 2
    val points = mutableListOf<Offset>()
    for (x in 0 until size.width.toInt()) {
        val y = (sin(x * (2f * PI / size.width)) * middleH + middleH).toFloat()
        points.add(Offset(x.toFloat(), y))
    }

    drawPoints(
        points = points,
        strokeWidth = 2f,
        pointMode = PointMode.Points,
        color = Color.Blue
    )
}
