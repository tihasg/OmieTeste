package com.fdlr.omieteste.domain.usecase

import com.fdlr.omieteste.domain.model.OrderWithItems
import com.fdlr.omieteste.domain.repository.OrderRepository

class GetOrdersUseCase(private val repository: OrderRepository) {
    suspend operator fun invoke(): List<OrderWithItems> {
        return repository.getOrdersWithItems()
    }
}