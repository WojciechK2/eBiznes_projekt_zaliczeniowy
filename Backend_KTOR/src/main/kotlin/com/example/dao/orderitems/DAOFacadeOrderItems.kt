package com.example.dao.orderitems

import com.example.models.OrderItem

interface DAOFacadeOrderItems {

    suspend fun createOrderItem(orderId: Int, clientId: String, productId: Int, quantity: Int): OrderItem?

}