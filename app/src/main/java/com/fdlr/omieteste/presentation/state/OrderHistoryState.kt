package com.fdlr.omieteste.presentation.state

import com.fdlr.omieteste.domain.model.OrderWithItems

sealed class OrderHistoryState {
    data object Loading : OrderHistoryState()
    data class OrdersHistory(val items: List<OrderWithItems>, val totalPrice: Double) : OrderHistoryState()
    data class Error(val message: String) : OrderHistoryState()
}
