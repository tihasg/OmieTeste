package com.fdlr.omieteste.domain.model

data class CartItem(
    var id: Int = 0,
    val product: CatalogItem,
    var quantity: Int = 0
)