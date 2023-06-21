package com.mobiledevpro.chart.view.ext

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import com.mobiledepro.main.domain.model.Candle
import com.mobiledepro.main.domain.model.calcEMA
import com.mobiledevpro.chart.view.drawCandle

@Composable
fun showChart(
    candleList: List<Candle>,
    candleWidth: Float,
    higherHighPrice: Double,
    pricePxFactor: Double,
    positiveCandleColor: Color,
    negativeCandleColor: Color
) {
    Spacer(modifier = Modifier.fillMaxSize().drawWithCache {
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
    period: Int,
    candleList: List<Candle>,
    candleWidth: Float,
    higherHighPrice: Double,
    pricePxFactor: Double,
    color: Color
) {
    Spacer(modifier = Modifier.fillMaxSize().drawWithCache {

        val emaPoints: List<Double> = candleList.calcEMA(period)

        val path = Path()
        var xPoint = 0f
        var yPoint = 0f

        emaPoints.forEachIndexed { index, price ->
            //println("Draw EMA 50: [$index] $price")
            xPoint = (candleWidth * index) + candleWidth / 2
            yPoint = ((higherHighPrice - price) / pricePxFactor).toFloat()

            if (index == 0)
                path.moveTo(xPoint, yPoint)

            path.lineTo(xPoint, yPoint)
        }

        onDrawWithContent {
            drawPath(path, color, style = Stroke(width = 1f))
        }
    })
}