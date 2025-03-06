package com.fdlr.omieteste.data.repository

import com.fdlr.omieteste.data.dao.OrderDao
import com.fdlr.omieteste.data.mappers.OrderWithItemsMapperImpl
import com.fdlr.omieteste.domain.model.OrderWithItems
import com.fdlr.omieteste.domain.repository.OrderRepository

class OrderRepositoryImpl(private val dao: OrderDao) : OrderRepository {
    override suspend fun getOrdersWithItems(): List<OrderWithItems> {
        return dao.getAllOrdersWithItems().map { OrderWithItemsMapperImpl().toDomain(it) }
    }

    override suspend fun getTotalPrice(): Double {
        return dao.getTotalPrice() ?: 0.0
    }

    override suspend fun addOrder(order: OrderWithItems) {
        dao.addOrder(OrderWithItemsMapperImpl().toEntity(order))
    }
}