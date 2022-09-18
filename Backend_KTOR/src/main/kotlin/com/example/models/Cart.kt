package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class ShoppingCart(
    val id: Int,
    val clientId: String,
)

@Serializable
data class ShoppingCartItem(
    val id: Int,
    val productId: Int,
    val quantity: Int,
    val shoppingCartId: Int
)

@Serializable
data class TransferShoppingCartItem(
    val productId: Int,
    //val quantity: Int
)

@Serializable
data class ReturnShoppingCartItem(
    val productName: String,
    val productId: Int,
    val quantity: Int,
    val price: Double
)

object ShoppingCarts : Table(){
    val id = integer("id").uniqueIndex().autoIncrement()
    val clientId = varchar("clientId",128).uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}

object ShoppingCartItems : Table(){
    val id = integer("id").uniqueIndex().autoIncrement()
    val productId = integer("productId").references(Products.id)
    val shoppingCartId = integer("shoppingCartId").references(ShoppingCarts.id)
    val quantity = integer("quantity")

    override val primaryKey = PrimaryKey(id)
}