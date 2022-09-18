package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Order(
    val id: Int,
    val clientId: String,
    val address: String,
    val phoneNumber: String,
    val totalPrice: Double
)

@Serializable
data class OrderItem(
    val id: Int,
    val clientId: String,
    val productId: Int,
    val orderId: Int,
    val quantity: Int
)

@Serializable
data class TransitOrderItem(
    val address: String,
    val phoneNumber: String,
)

@Serializable
data class ReturnOrderId(
    val id: Int,
)

object Orders : Table(){
    val id = integer("id").uniqueIndex().autoIncrement()
    val clientId = varchar("clientId", 128)
    val address = varchar("address", 128)
    val phoneNumber = varchar("phoneNumber", 128)
    val totalPrice = double("totalPrice").default(0.0)

    override val primaryKey = PrimaryKey(id)
}

object OrderItems : Table(){
    val id = integer("id").uniqueIndex().autoIncrement()
    val clientId = varchar("clientId", 128)
    val productId = integer("producId").references(Products.id)
    val orderId = integer("orderId").references(Orders.id)
    val quantity = integer("quantity")

    override val primaryKey = PrimaryKey(id)
}