package com.fdlr.omieteste.presentation.ui.catalog.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun CurrencyInputField(
    value: Double,
    onValueChange: (Double) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    navigateToNext: () -> Unit = {}
) {
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = formatCurrency(value),
                selection = TextRange(formatCurrency(value).length)
            )
        )
    }

    Box {
        OutlinedTextField(
            value = textFieldValue,
            maxLines = 1,
            onValueChange = { newValue ->
                val cleanedInput = newValue.text.replace("[^\\d]".toRegex(), "")

                val numericValue =
                    if (cleanedInput.isEmpty()) 0.0 else cleanedInput.toDouble() / 100

                val formattedValue = formatCurrency(numericValue)
                val selectionIndex =
                    calculateSelectionIndex(newValue.text, formattedValue, newValue.selection.start)

                textFieldValue =
                    TextFieldValue(formattedValue, selection = TextRange(selectionIndex))

                onValueChange(numericValue)
            },
            label = { Text(label) },
            modifier = modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(onDone = { navigateToNext() }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = imeAction
            )
        )
    }
}

private fun calculateSelectionIndex(
    originalValue: String,
    formattedValue: String,
    originalSelectionIndex: Int
): Int {
    val originalDigits = originalValue.replace("[^\\d]".toRegex(), "")
    val formattedDigits = formattedValue.replace("[^\\d]".toRegex(), "")

    val digitDifference = originalDigits.length - formattedDigits.length
    val newSelectionIndex = originalSelectionIndex - digitDifference
    return newSelectionIndex.coerceIn(0, formattedValue.length)
}

private fun formatCurrency(value: Double): String {
    return "R$ " + String.format("%,.2f", value)
}

