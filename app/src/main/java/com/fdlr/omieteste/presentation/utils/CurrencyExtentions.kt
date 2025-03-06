package com.fdlr.omieteste.presentation.utils

import java.text.NumberFormat
import java.util.Locale

fun Double?.toBrazilianCurrency(): String {
    if (this == null) return "R$ 0,00"
    val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return formatter.format(this)
}


fun String.toCurrency(): Double {
    return if (this.isNotEmpty()) {
        try {
            val cleanString = this.replace("[^\\d,]".toRegex(), "")

            val parsed = if (cleanString.contains(",")) {
                cleanString.replace(",", ".").toDouble()
            } else {
                cleanString.toDouble()
            }

            parsed
        } catch (e: Exception) {
            0.0
        }
    } else {
        0.0
    }
}