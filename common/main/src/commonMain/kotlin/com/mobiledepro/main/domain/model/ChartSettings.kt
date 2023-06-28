package com.mobiledepro.main.domain.model

import androidx.compose.ui.graphics.Color

data class ChartSettings(
    var ema50: Boolean = false,
    var ema200: Boolean = false,
    var emaRibbon: Boolean = false
) {
    fun getRibbonMap(): Map<Int, Color> =
        HashMap<Int, Color>().apply {
            set(20, Color(0xFFf5eb5d))
            set(25, Color(0xFFf5b771))
            set(30, Color(0xFFf5b056))
            set(35, Color(0xFFf57b4e))
            set(40, Color(0xFFf56d58))
            set(45, Color(0xFFf57d51))
            set(50, Color(0xFFf55151))
            set(55, Color(0xFFaa2707))
        }
}
