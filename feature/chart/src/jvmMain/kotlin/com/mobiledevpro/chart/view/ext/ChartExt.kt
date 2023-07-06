package com.mobiledevpro.chart.view.ext

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import com.mobiledepro.main.domain.model.Candle
import com.mobiledevpro.chart.view.drawCandle

@Composable
fun showChart(
    candleList: List<Candle>,
    candleWidth: Float,
    higherHighPrice: Double,
    pricePxFactor: Double,
    positiveCandleColor: Color,
    negativeCandleColor: Color,
    modifier: Modifier,
) {
    Spacer(modifier = modifier.drawWithCache {
        onDrawBehind {
            candleList.forEachIndexed { index, candle ->
                drawCandle(
                    width = candleWidth - 1, //1px for space between candles
                    offsetX = (candleWidth * index),
                    lowY = (higherHighPrice.minus(candle.priceLow) / pricePxFactor).toFloat(),
                    highY = (higherHighPrice.minus(candle.priceHigh) / pricePxFactor).toFloat(),
                    openY = (higherHighPrice.minus(candle.priceOpen) / pricePxFactor).toFloat(),
                    closeY = (higherHighPrice.minus(candle.priceClose) / pricePxFactor).toFloat(),
                    color = if (candle.priceOpen < candle.priceClose) positiveCandleColor else negativeCandleColor
                )
            }
        }
    })
}

@Composable
fun showEMALine(
    chartSize: Size,
    emaPricePoints: List<Double>,
    candleWidth: Float,
    higherHighPrice: Double,
    pricePxFactor: Double,
    color: Color,
    modifier: Modifier
) {
    Spacer(modifier = modifier.drawWithCache {

        val points = ArrayList<Offset>()

        emaPricePoints.forEachIndexed { index, price ->
            val xPoint = (candleWidth * index) + candleWidth / 2
            val yPoint = ((higherHighPrice - price) / pricePxFactor).toFloat()

            //Don't draw outside the chart box
            if (xPoint <= chartSize.width && yPoint <= chartSize.height)
                Offset(x = xPoint, y = yPoint)
                    .also(points::add)
        }

        onDrawWithContent {
            drawPoints(
                points = points,
                pointMode = PointMode.Polygon,
                color = color,
                cap = StrokeCap.Round,
                strokeWidth = 1f
            )
        }
    })
}