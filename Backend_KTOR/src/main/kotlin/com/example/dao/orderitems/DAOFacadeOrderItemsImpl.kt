package com.example.dao.orderitems

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.OrderItem
import com.example.models.OrderItems
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert

class DAOFacadeOrderItemsImpl : DAOFacadeOrderItems {

    private fun resultRowToOrderItem(row: ResultRow) = OrderItem(
        id = row[OrderItems.id],
        clientId = row[OrderItems.clientId],
        productId = row[OrderItems.productId],
        orderId = row[OrderItems.orderId],
        quantity = row[OrderItems.quantity]
    )

    override suspend fun createOrderItem(orderId: Int, clientId: String, productId: Int, quantity: Int): OrderItem? = dbQuery{
        val insertStatement = OrderItems.insert {
            it[OrderItems.clientId] = clientId
            it[OrderItems.orderId] = orderId
            it[OrderItems.productId] = productId
            it[OrderItems.quantity] = quantity
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToOrderItem)
    }

}

val daoOrderItems : DAOFacadeOrderItems = DAOFacadeOrderItemsImpl()