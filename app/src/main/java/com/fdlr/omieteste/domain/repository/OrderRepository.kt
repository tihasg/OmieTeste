package com.fdlr.omieteste.domain.repository

import com.fdlr.omieteste.domain.model.OrderWithItems

interface OrderRepository {
    suspend fun getOrdersWithItems(): List<OrderWithItems>
    suspend fun getTotalPrice(): Double
    suspend fun addOrder(order: OrderWithItems)
}