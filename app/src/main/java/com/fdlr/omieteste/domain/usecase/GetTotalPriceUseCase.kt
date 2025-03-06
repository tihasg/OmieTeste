package com.fdlr.omieteste.domain.usecase

import com.fdlr.omieteste.domain.repository.OrderRepository

class GetTotalPriceUseCase (private val repository: OrderRepository){
    suspend operator fun invoke(): Double {
        return repository.getTotalPrice()
    }
}