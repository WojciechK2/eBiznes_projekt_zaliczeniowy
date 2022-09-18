package com.example.dao.orders

import com.example.models.Order

interface DAOFacadeOrders {
    suspend fun getOrderByOrderId(orderId: Int) : Order?
    suspend fun createOrder(cartId: Int, clientId: String, address: String, phoneNumber: String) : Order?
    suspend fun deleteOrder(orderId: Int, clientId: String) : Boolean
    suspend fun setTotalPrice(orderId: Int, totalPrice: Double) : Boolean
}