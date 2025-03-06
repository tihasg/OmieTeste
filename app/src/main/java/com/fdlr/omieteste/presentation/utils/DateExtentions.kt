package com.fdlr.omieteste.presentation.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun String.toBrazilianDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        .withZone(ZoneId.systemDefault())
    val instant = Instant.parse(this)
    return formatter.format(instant)
}