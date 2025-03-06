package com.fdlr.omieteste.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fdlr.omieteste.domain.usecase.GetOrdersUseCase
import com.fdlr.omieteste.domain.usecase.GetTotalPriceUseCase
import com.fdlr.omieteste.presentation.state.OrderHistoryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OrderHistoryViewModel(
    private val getTotalPrice: GetTotalPriceUseCase,
    private val getOrders: GetOrdersUseCase
) : ViewModel() {
    private val _ordersState = MutableStateFlow<OrderHistoryState>(OrderHistoryState.Loading)
    val ordersState =  _ordersState
        .onStart {
            fetchOrders()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _ordersState.value
        )

    private fun fetchOrders() {
        viewModelScope.launch {
            _ordersState.value = OrderHistoryState.OrdersHistory(getOrders(), getTotalPrice())
        }
    }
}