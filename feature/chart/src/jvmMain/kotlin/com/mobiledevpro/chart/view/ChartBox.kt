package com.mobiledevpro.chart.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.mobiledepro.main.domain.model.Chart
import com.mobiledepro.main.domain.model.ChartSettings
import com.mobiledepro.main.domain.model.toEMAPrice
import com.mobiledevpro.chart.view.ext.showChart
import com.mobiledevpro.chart.view.ext.showEMALine
import com.mobiledevpro.ui.component.WidgetBox
import com.mobiledevpro.ui.ema200Color
import com.mobiledevpro.ui.ema50Color
import com.mobiledevpro.ui.negativeCandleColor
import com.mobiledevpro.ui.positiveCandleColor

@Composable
fun ChartBox(chart: Chart, chartSettings: ChartSettings, modifier: Modifier = Modifier) {

    val coroutineScope = rememberCoroutineScope()

    val higherHighPrice = remember { mutableStateOf(0.0) }
    val lowerLowPrice = remember { mutableStateOf(0.0) }
    val pricePxFactor = remember { mutableStateOf(0.0) }
    val candleWith = remember { mutableStateOf(0f) }
    val chartSize = remember { mutableStateOf(Size(0f, 0f)) }

    WidgetBox(modifier = modifier) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            println("Chart Canvas")

            higherHighPrice.value = chart.getHigherHighPrice()
            lowerLowPrice.value = chart.getLowerLowPrice()

            chartSize.value = size

            //Find price movement for 1 px
            pricePxFactor.value = higherHighPrice.value.minus(lowerLowPrice.value) / size.height

            //Find candle width
            candleWith.value = size.width / chart.candlesCount()

            drawXAxis(size)
            drawYAxis(size)
        }

        if (higherHighPrice.value == 0.0 || lowerLowPrice.value == 0.0) return@WidgetBox

        //Draw the chart
        showChart(
            candleList = chart.getLimitedCandleList(),
            candleWidth = candleWith.value,
            higherHighPrice = higherHighPrice.value,
            pricePxFactor = pricePxFactor.value,
            positiveCandleColor = MaterialTheme.colors.positiveCandleColor,
            negativeCandleColor = MaterialTheme.colors.negativeCandleColor
        )


        if (chartSettings.ema50)
        //Draw EMA 50
            showEMALine(
                chartSize = chartSize.value,
                emaPricePoints = chart.candleList.toEMAPrice(50),
                candleWidth = candleWith.value,
                higherHighPrice = higherHighPrice.value,
                pricePxFactor = pricePxFactor.value,
                color = MaterialTheme.colors.ema50Color,
                modifier = Modifier.fillMaxSize()
            )

        if (chartSettings.ema200)
        //Draw EMA 200
            showEMALine(
                chartSize = chartSize.value,
                emaPricePoints = chart.candleList.toEMAPrice(200),
                candleWidth = candleWith.value,
                higherHighPrice = higherHighPrice.value,
                pricePxFactor = pricePxFactor.value,
                color = MaterialTheme.colors.ema200Color,
                modifier = Modifier.fillMaxSize()
            )

    }

}

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