package com.fdlr.omieteste.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fdlr.omieteste.domain.model.CartItem
import com.fdlr.omieteste.domain.model.CatalogItem
import com.fdlr.omieteste.domain.model.OrderItemDetail
import com.fdlr.omieteste.domain.model.OrderWithItems
import com.fdlr.omieteste.domain.usecase.AddCatalogItemUseCase
import com.fdlr.omieteste.domain.usecase.AddOrderUseCase
import com.fdlr.omieteste.domain.usecase.GetCatalogItemsUseCase
import com.fdlr.omieteste.presentation.intent.CatalogIntent
import com.fdlr.omieteste.presentation.state.CatalogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CatalogViewModel(
    private val addCatalogItem: AddCatalogItemUseCase,
    private val getCatalogItems: GetCatalogItemsUseCase,
    private val addOrder: AddOrderUseCase,
) : ViewModel() {

    private val _catalogState = MutableStateFlow<CatalogState>(CatalogState.Loading)
    val catalogState = _catalogState
        .onStart {
            fetchCatalogItems()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _catalogState.value
        )

    private val catalogItems = mutableListOf<CatalogItem>()
    private val cartItems = mutableListOf<CartItem>()


    private fun fetchCatalogItems() {
        viewModelScope.launch {
            try {
                val items = getCatalogItems()
                _catalogState.value = CatalogState.CartAndTotalItems(
                    items.map { CartItem(product = it) }
                )
                catalogItems.clear()
                catalogItems.addAll(items)
                updateCartState()
            } catch (e: Exception) {
                _catalogState.value = CatalogState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun processIntent(intent: CatalogIntent) {
        when (intent) {
            is CatalogIntent.AddItemToCart -> addItemToCart(intent.item)
            is CatalogIntent.RemoveItemFromCart -> removeItemFromCart(intent.item)
            is CatalogIntent.UpdateCartQuantity -> updateCartQuantity(intent.quantity, intent.item)
            is CatalogIntent.CreateNewCatalogItem -> createNewCatalogItem(intent.item)
            is CatalogIntent.CreateOrder -> createOrder(intent.clientName)
        }
    }

    private fun createOrder(clientName: String) {
        viewModelScope.launch {
            _catalogState.value = CatalogState.Loading
            addOrder(
                OrderWithItems(
                    totalPrice = cartItems.sumOf { it.product.price * it.quantity },
                    customerName = clientName,
                    items = cartItems.map {
                        OrderItemDetail(
                            name = it.product.name,
                            quantity = it.quantity,
                            price = it.product.price,
                            imagePath = it.product.imagePath
                        )
                    }
                )
            )
            _catalogState.value = CatalogState.NavigateToOrderHistory
        }
    }

    private fun createNewCatalogItem(item: CatalogItem) {
        viewModelScope.launch {
            _catalogState.value = CatalogState.Loading
            addCatalogItem(item)
            fetchCatalogItems()
        }
    }

    private fun addItemToCart(item: CatalogItem) {
        viewModelScope.launch {
            val existingCartItem = cartItems.find { it.product.id == item.id }
            if (existingCartItem != null) {
                existingCartItem.quantity++
            } else {
                cartItems.add(CartItem(product = item, quantity = 1))
            }
            updateCartState()
        }
    }

    private fun removeItemFromCart(item: CartItem) {
        viewModelScope.launch {
            val existingCartItem = cartItems.find { it.product.id == item.product.id }
            if (existingCartItem != null) {
                if (existingCartItem.quantity > 1) {
                    existingCartItem.quantity--
                } else {
                    cartItems.remove(existingCartItem)
                }
            }
            updateCartState()
        }
    }

    private fun updateCartQuantity(quantity: Int, item: CartItem) {
        viewModelScope.launch {
            if (quantity > 0) {
                val existingCartItem = cartItems.find { it.product.id == item.product.id }
                existingCartItem?.quantity = quantity
            } else {
                removeItemFromCart(item)
            }
            updateCartState()
        }
    }

    private fun updateCartState() {
        val updatedCatalogItems = catalogItems.map { catalogItem ->
            val cartItem = cartItems.find { it.product.id == catalogItem.id }
            if (cartItem != null) {
                catalogItem to cartItem.quantity
            } else {
                catalogItem to 0
            }
        }
        _catalogState.value = CatalogState.CartAndTotalItems(
            cartItems = updatedCatalogItems.map { (catalogItem, quantity) ->
                CartItem(product = catalogItem, quantity = quantity)
            },
            totalItems = cartItems.sumOf { it.quantity },
            totalValue = cartItems.sumOf { it.product.price * it.quantity }
        )
    }
}
