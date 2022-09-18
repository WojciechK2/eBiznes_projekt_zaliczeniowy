package com.example.dao.orders

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.Order
import com.example.models.Orders
import org.jetbrains.exposed.sql.*

class DAOFacadeOrdersImpl : DAOFacadeOrders {

    private fun resultRowToOrder(row: ResultRow) = Order(
        id = row[Orders.id],
        clientId = row[Orders.clientId],
        address = row[Orders.address],
        phoneNumber = row[Orders.phoneNumber],
        totalPrice = row[Orders.totalPrice],
    )

    override suspend fun getOrderByOrderId(orderId: Int): Order? = dbQuery{
        Orders.select{
            Orders.id eq orderId
        }.map(::resultRowToOrder).singleOrNull()
    }

    override suspend fun createOrder(cartId: Int, clientId: String, address: String, phoneNumber: String): Order? = dbQuery {
        val insertStatement = Orders.insert {
            it[Orders.address] = address
            it[Orders.phoneNumber] = phoneNumber
            it[Orders.clientId] = clientId
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToOrder)
    }

    override suspend fun deleteOrder(orderId: Int, clientId: String): Boolean = dbQuery{
        Orders.deleteWhere { Orders.id eq orderId} > 0
    }

    override suspend fun setTotalPrice(orderId: Int, totalPrice: Double): Boolean = dbQuery{
        Orders.update ({Orders.id eq orderId}){
            it[Orders.totalPrice] = totalPrice
        } > 0
    }

}

val daoOrders : DAOFacadeOrders = DAOFacadeOrdersImpl()