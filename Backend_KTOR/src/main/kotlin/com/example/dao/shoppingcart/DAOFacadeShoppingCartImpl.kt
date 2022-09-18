package com.example.dao.shoppingcart

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.*
import org.jetbrains.exposed.sql.*

class DAOFacadeShoppingCartImpl : DAOFacadeShoppingCart {

    private fun resultRowToShoppingCartItem(row: ResultRow) = ShoppingCartItem(
        id = row[ShoppingCartItems.id],
        productId = row[ShoppingCartItems.productId],
        quantity = row[ShoppingCartItems.quantity],
        shoppingCartId = row[ShoppingCartItems.shoppingCartId]
    )

    private fun resultRowToShoppingCart(row: ResultRow) = ShoppingCart(
        id = row[ShoppingCarts.id],
        clientId = row[ShoppingCarts.clientId]
    )

    private fun resultRowToReturnShoppingCartItem(row: ResultRow) = ReturnShoppingCartItem(
        productName = row[Products.name],
        quantity = row[ShoppingCartItems.quantity],
        productId = row[ShoppingCartItems.productId],
        price = row[Products.price]
    )

    override suspend fun addCart(clientId: String): ShoppingCart? = dbQuery{
        val insertStatement = ShoppingCarts.insert{
            it[ShoppingCarts.clientId] = clientId
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToShoppingCart)
    }

    override suspend fun getItems(shoppingCartId: Int): List<ReturnShoppingCartItem> = dbQuery{
        ShoppingCartItems.join(Products, JoinType.INNER, additionalConstraint = {(Products.id eq ShoppingCartItems.productId) and (ShoppingCartItems.shoppingCartId eq shoppingCartId)})
            .slice(Products.name,ShoppingCartItems.productId,ShoppingCartItems.quantity, Products.price)
            .selectAll()
            .map(::resultRowToReturnShoppingCartItem)
    }

    override suspend fun getItem(shoppingCartId: Int, productId: Int): ShoppingCartItem? = dbQuery{
        ShoppingCartItems.select {
            (ShoppingCartItems.shoppingCartId eq shoppingCartId) and (ShoppingCartItems.productId eq productId)
        }.map(::resultRowToShoppingCartItem).singleOrNull()
    }

    override suspend fun addItem(shoppingCartId: Int, productId: Int): ShoppingCartItem? = dbQuery {
        val insertStatement = ShoppingCartItems.insert {
            it[ShoppingCartItems.shoppingCartId] = shoppingCartId
            it[ShoppingCartItems.productId] = productId
            it[quantity] = 1
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToShoppingCartItem)
    }

    override suspend fun removeItem(shoppingCartId: Int, productId: Int): Boolean = dbQuery {
        ShoppingCartItems.deleteWhere { (ShoppingCartItems.shoppingCartId eq shoppingCartId) and (ShoppingCartItems.productId eq productId) } > 0
    }

    override suspend fun updateItem(shoppingCartId: Int, productId: Int, quantity: Int): Boolean = dbQuery{
        ShoppingCartItems.update ({(ShoppingCartItems.shoppingCartId eq shoppingCartId) and (ShoppingCartItems.productId eq productId)}){
            it[ShoppingCartItems.quantity] = quantity
        } > 0
    }

    override suspend fun getCart(clientId: String): ShoppingCart? = dbQuery {
        ShoppingCarts
            .select { ShoppingCarts.clientId eq clientId }
            .map(::resultRowToShoppingCart)
            .singleOrNull()
    }
}

val daoCarts : DAOFacadeShoppingCart = DAOFacadeShoppingCartImpl()