package com.fdlr.omieteste.domain.usecase

import com.fdlr.omieteste.domain.model.OrderWithItems
import com.fdlr.omieteste.domain.repository.OrderRepository

class AddOrderUseCase(private val repository: OrderRepository) {
    suspend operator fun invoke(order: OrderWithItems) {
        repository.addOrder(order)
    }
}