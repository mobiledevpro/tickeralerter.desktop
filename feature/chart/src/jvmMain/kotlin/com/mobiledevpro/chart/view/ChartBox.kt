package com.mobiledevpro.chart.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.Chart
import com.mobiledepro.main.domain.model.ChartSettings
import com.mobiledepro.main.domain.model.toEMAPrice
import com.mobiledevpro.chart.view.component.showChart
import com.mobiledevpro.chart.view.component.showEMALine
import com.mobiledevpro.chart.view.ext.drawXAxis
import com.mobiledevpro.chart.view.ext.drawYAxis
import com.mobiledevpro.chart.view.state.ChartUIState
import com.mobiledevpro.ui.*
import com.mobiledevpro.ui.common.modifierMaxSize
import com.mobiledevpro.ui.component.WidgetBox

@Composable
fun ChartBox(state: ChartUIState, chartSettings: ChartSettings, modifier: Modifier = Modifier) {

    var chartSize by remember { mutableStateOf(Size(0f, 0f)) }

    WidgetBox(modifier = modifier) {

        Canvas(modifier = modifierMaxSize) {
            println("Chart Canvas: state $state")
            chartSize = size
            drawXAxis(size)
            drawYAxis(size)
        }

        when (state) {
            is ChartUIState.Success -> Chart(state.chartData, chartSettings, chartSize)
            is ChartUIState.Empty -> Empty()
            is ChartUIState.Loading -> Loading()
        }

    }

}

@Composable
fun Chart(
    chart: Chart,
    chartSettings: ChartSettings,
    chartSize: Size
) {

    val higherHighPrice = chart.getHigherHighPrice()
    val lowerLowPrice = chart.getLowerLowPrice()

    if (higherHighPrice == 0.0 || lowerLowPrice == 0.0) return

    println("Chart candles ${chart.candleList.size}")

    //Find price movement for 1 px
    val pricePxFactor = higherHighPrice.minus(lowerLowPrice) / chartSize.height

    //Find candle width
    val candleWith = chartSize.width / chart.candlesCount()

    //Draw the chart
    showChart(
        candleList = chart.getLimitedCandleList(),
        candleWidth = candleWith,
        higherHighPrice = higherHighPrice,
        pricePxFactor = pricePxFactor,
        positiveCandleColor = MaterialTheme.colors.positiveCandleColor,
        negativeCandleColor = MaterialTheme.colors.negativeCandleColor,
        modifier = modifierMaxSize,
    )

    //DRAW INDICATORS

    //Draw EMA 50
    if (chartSettings.ema50)
        showEMALine(
            chartSize = chartSize,
            emaPricePoints = chart.candleList.toEMAPrice(50),
            candleWidth = candleWith,
            higherHighPrice = higherHighPrice,
            pricePxFactor = pricePxFactor,
            color = MaterialTheme.colors.ema50Color,
            modifier = modifierMaxSize
        )

    //Draw EMA 200
    if (chartSettings.ema200)
        showEMALine(
            chartSize = chartSize,
            emaPricePoints = chart.candleList.toEMAPrice(200),
            candleWidth = candleWith,
            higherHighPrice = higherHighPrice,
            pricePxFactor = pricePxFactor,
            color = MaterialTheme.colors.ema200Color,
            modifier = modifierMaxSize
        )

    //Draw EMA Ribbon lines
    if (chartSettings.emaRibbon)
        chartSettings.getRibbonMap().forEach { (period: Int, lineColor: Color) ->
            showEMALine(
                chartSize = chartSize,
                emaPricePoints = chart.candleList.toEMAPrice(period),
                candleWidth = candleWith,
                higherHighPrice = higherHighPrice,
                pricePxFactor = pricePxFactor,
                color = lineColor,
                modifier = modifierMaxSize
            )
        }
}

@Composable
fun Empty() {
    Box(
        modifier = modifierMaxSize,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Please select a symbol in Watchlist to see the chart.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.white),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun Loading() {
    Box(
        modifier = modifierMaxSize,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Loading...",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.white),
            modifier = Modifier.padding(16.dp)
        )

    }
}
