package com.fdlr.omieteste.presentation.intent

import com.fdlr.omieteste.domain.model.CartItem
import com.fdlr.omieteste.domain.model.CatalogItem

sealed class CatalogIntent {
    data class AddItemToCart(val item: CatalogItem) : CatalogIntent()
    data class RemoveItemFromCart(val item: CartItem) : CatalogIntent()
    data class UpdateCartQuantity(val quantity: Int, val item: CartItem) : CatalogIntent()
    data class CreateNewCatalogItem(val item: CatalogItem) : CatalogIntent()
    data class CreateOrder(val clientName: String) : CatalogIntent()
}