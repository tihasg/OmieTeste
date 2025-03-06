package com.fdlr.omieteste.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val totalPrice: Double,
    val date: String,
    val customerName: String,
    val items: List<CatalogItemEntity> = emptyList()
)