package com.fdlr.omieteste.presentation.state

import com.fdlr.omieteste.domain.model.CartItem

sealed class CatalogState {
    data object Loading : CatalogState()
    data class Error(val message: String) : CatalogState()
    data class CartAndTotalItems(
        val cartItems: List<CartItem>,
        val totalItems: Int = 0,
        val totalValue: Double = 0.0
    ) : CatalogState()
    data object NavigateToOrderHistory : CatalogState()
}

