package com.mobiledevpro.alert.settings.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mobiledepro.main.domain.model.*
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.component.SelectValueField

@Composable
internal fun ConditionRules(
    tickerList: List<Ticker>,
    trigger: AlertTrigger,
    onChange: (AlertTrigger) -> Unit
) {
    println("::RULES FOR ${trigger.symbol} | ${trigger.alertSettings.targetPrice}")

    val targetPrice: (symbol: String) -> Double? = { symbol ->
        if (trigger.alertSettings.conditionTarget == ConditionTarget.PRICE)
            tickerList.find { it.symbol == symbol }?.lastPrice
        else
            null
    }

    val price: Double? by remember { mutableStateOf(trigger.alertSettings.targetPrice ?: targetPrice(trigger.symbol)) }

    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        TextLabel("Condition")

        Column(modifier = modifierMaxWidth.weight(0.7f)) {
            //Symbol
            SelectValueField(
                modifier = modifierMaxWidth,
                defaultValue = trigger.symbol,
                valueList = tickerList.mapTo(ArrayList<String>()) { it.symbol },
                onSelect = { symbol ->
                    trigger.apply {
                        this.symbol = symbol
                    }.updateTargetPrice(targetPrice(symbol))
                        .also(onChange)
                }
            )

            //Condition type
            SelectValueField(
                modifier = modifierMaxWidth,
                valueList = conditionTypeList(),
                onSelect = { conditionType ->
                    trigger
                        .updateConditionType(conditionType.toConditionType())
                        .also(onChange)
                })

            //Condition target
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifierMaxWidth) {
                SelectValueField(
                    modifier = modifierMaxWidth.weight(1f),
                    defaultValue = trigger.alertSettings.conditionTarget.toStr(),
                    valueList = conditionTargetList(),
                    onSelect = { conditionTarget ->
                        trigger.updateConditionTarget(conditionTarget.toConditionTarget())
                            .updateTargetPrice(targetPrice(trigger.symbol))
                            .also(onChange)
                    })


                if (trigger.alertSettings.conditionTarget == ConditionTarget.PRICE)
                    SelectValueField(
                        modifier = modifierMaxWidth.weight(1f),
                        defaultValue = price.toString(),
                        valueList = emptyList(),
                        onSelect = {}
                    )
            }
        }

    }
}

