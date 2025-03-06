package com.fdlr.omieteste.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fdlr.omieteste.data.model.OrderEntity

@Dao
interface OrderDao {
    @Insert
    suspend fun insertOrder(order: OrderEntity): Long

    @Query("SELECT * FROM orders")
    suspend fun getAllOrdersWithItems(): List<OrderEntity>

    @Query("SELECT SUM(totalPrice) FROM orders")
    suspend fun getTotalPrice(): Double?

    @Insert
    suspend fun addOrder(order: OrderEntity)
}