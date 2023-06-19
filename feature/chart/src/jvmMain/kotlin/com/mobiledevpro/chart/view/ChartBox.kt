package com.mobiledevpro.chart.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.mobiledepro.main.domain.model.Candle
import com.mobiledevpro.ui.component.WidgetBox
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun ChartBox(candleList: List<Candle>, modifier: Modifier = Modifier) {
    println("Candle list ${candleList.size}")
    WidgetBox(modifier = modifier) {
        //  Text("Chart here: candle list ${candleList.size}", modifier = Modifier.align(Alignment.Center))

        Canvas(modifier = Modifier.fillMaxSize()) {
            // your drawing code goes here
            testDrawing()

        }

    }
}

fun DrawScope.drawXAxis() {

}

fun DrawScope.drawYAxis() {

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
        start = Offset(0f, 0f), // values in pixels
        end = Offset(0f, size.height),
        strokeWidth = 1f // values in pixels
    )

    drawLine(
        color = Color.DarkGray,
        start = Offset(0f, size.height), // values in pixels
        end = Offset(size.width, size.height),
        strokeWidth = 1f // values in pixels
    )

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
