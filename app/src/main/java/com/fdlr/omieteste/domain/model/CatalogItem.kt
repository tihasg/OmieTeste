package com.fdlr.omieteste.domain.model

import com.fdlr.omieteste.data.model.CatalogItemEntity

data class CatalogItem(
    val id: Long = 0,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int = 0,
    val imagePath: String? = null
) {
    fun toEntity() = CatalogItemEntity(
        name = name,
        description = description,
        price = price,
        quantity = quantity,
        imagePath = imagePath
    )
}
