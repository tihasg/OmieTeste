package com.fdlr.omieteste.domain.model

data class OrderItemDetail(
    val name: String,
    val quantity: Int,
    val price: Double,
    val imagePath: String?
)