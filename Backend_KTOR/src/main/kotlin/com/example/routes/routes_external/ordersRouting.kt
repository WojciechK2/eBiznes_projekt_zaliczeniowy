package com.example.routes.routes_external

import com.example.dao.orderitems.daoOrderItems
import com.example.dao.orders.daoOrders
import com.example.dao.shoppingcart.daoCarts
import com.example.models.*
import com.example.plugins.UserSession
import com.example.utils.identityCheck
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.coroutines.runBlocking

fun Route.ordersRouting(){
    authenticate("auth-session") {
        route("/orders") {
            post("/add"){
                val userSession = call.sessions.get<UserSession>() ?: return@post call.respondText(
                    "Please log in to access your cart",
                    status = HttpStatusCode.Forbidden
                )
                val orderParams = call.receive<TransitOrderItem>()
                val cart = identityCheck(userSession)
                val order = daoOrders.createOrder(cart.id, cart.clientId, orderParams.address, orderParams.phoneNumber) ?: return@post call.respondText(
                    "Error while submitting the order",
                    status = HttpStatusCode.InternalServerError
                )
                insertElementsFromCartsToOrderItem(order,cart)
                call.respond(ReturnOrderId(order.id))
            }
        }
    }
}

fun insertElementsFromCartsToOrderItem(order: Order, cart: ShoppingCart ){
    runBlocking {
        val itemsToMove = daoCarts.getItems(cart.id)
        var totalPrice = 0.0
        for (item in itemsToMove) {
            //section here lacks error handling
            daoOrderItems.createOrderItem(order.id, order.clientId, item.productId, item.quantity)
            daoCarts.removeItem(cart.id, item.productId)
            totalPrice += (item.price * item.quantity)
        }
        //println(totalPrice)
        daoOrders.setTotalPrice(order.id, totalPrice)
    }
}