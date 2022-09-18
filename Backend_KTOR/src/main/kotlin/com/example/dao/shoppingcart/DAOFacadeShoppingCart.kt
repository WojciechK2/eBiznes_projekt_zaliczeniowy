package com.example.dao.shoppingcart

import com.example.models.ReturnShoppingCartItem
import com.example.models.ShoppingCart
import com.example.models.ShoppingCartItem

interface DAOFacadeShoppingCart {
    suspend fun addCart(clientId: String): ShoppingCart?
    suspend fun getItems(shoppingCartId: Int) : List<ReturnShoppingCartItem>
    suspend fun getItem(shoppingCartId: Int, productId: Int) : ShoppingCartItem?
    suspend fun addItem(shoppingCartId: Int, productId: Int) : ShoppingCartItem? // only first time
    suspend fun removeItem(shoppingCartId: Int, productId: Int) : Boolean // delete
    suspend fun updateItem(shoppingCartId: Int, productId: Int, quantity: Int ) : Boolean // -1 or +1
    suspend fun getCart(clientId: String): ShoppingCart?
}