package com.fdlr.omieteste.domain.model

import java.time.Instant
import java.time.format.DateTimeFormatter

data class OrderWithItems(
    val id: Long = 0,
    val totalPrice: Double,
    val date: String = DateTimeFormatter.ISO_INSTANT.format(Instant.now()),
    val customerName: String,
    val items: List<OrderItemDetail>
)
