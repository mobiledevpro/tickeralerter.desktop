package com.mobiledevpro.alert.settings.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import com.mobiledepro.main.domain.model.*
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.ui.component.InputValueField
import com.mobiledevpro.ui.component.InputValueType
import com.mobiledevpro.ui.component.SelectValueField

@Composable
internal fun ConditionRules(
    tickerList: List<Ticker>,
    trigger: AlertTrigger,
    onChange: (AlertTrigger) -> Unit
) {
    println("::RULES FOR ${trigger.symbol} | ${trigger.alertSettings.targetPrice}")

    var selectedSymbol by remember { mutableStateOf(trigger.symbol) }
    var selectedConditionType by remember { mutableStateOf(trigger.alertSettings.conditionType) }
    var selectedConditionTarget by remember { mutableStateOf(trigger.alertSettings.conditionTarget) }
    var editPrice: Double by remember { mutableStateOf(trigger.alertSettings.targetPrice ?: 0.0) }

    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        TextLabel("Condition")

        Column(modifier = modifierMaxWidth.weight(0.7f)) {
            //Symbol
            SelectValueField(
                modifier = modifierMaxWidth,
                defaultValue = selectedSymbol,
                valueList = tickerList.mapTo(ArrayList<String>()) { it.symbol },
                onSelect = { symbol ->
                    selectedSymbol = symbol
                    editPrice = tickerList.find { it.symbol == symbol }?.lastPrice ?: 0.0

                    trigger.updateSymbol(symbol)
                        .updateTargetPrice(editPrice)
                        .also(onChange)
                }
            )

            //Condition type
            SelectValueField(
                modifier = modifierMaxWidth,
                valueList = conditionTypeList(),
                defaultValue = selectedConditionType.toStr(),
                onSelect = { conditionType ->
                    selectedConditionType = conditionType.toConditionType()

                    trigger.updateConditionType(selectedConditionType)
                        .also(onChange)
                })

            //Condition target
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifierMaxWidth) {
                SelectValueField(
                    modifier = modifierMaxWidth.weight(1f),
                    defaultValue = selectedConditionTarget.toStr(),
                    valueList = conditionTargetList(),
                    onSelect = { conditionTarget ->
                        selectedConditionTarget = conditionTarget.toConditionTarget()

                        trigger.updateConditionTarget(selectedConditionTarget)
                            .also(onChange)
                    })


                if (selectedConditionTarget == ConditionTarget.PRICE)
                    InputValueField(
                        modifier = modifierMaxWidth.weight(1f),
                        value = editPrice.toString(),
                        hint = "0.0",
                        type = InputValueType.PRICE,
                        onTextChanged = { text ->
                            editPrice = try {
                                text.toDouble()
                            } catch (e: NumberFormatException) {
                                0.0
                            }
                            trigger.updateTargetPrice(editPrice)
                                .also(onChange)
                        }
                    )
            }
        }

    }
}


