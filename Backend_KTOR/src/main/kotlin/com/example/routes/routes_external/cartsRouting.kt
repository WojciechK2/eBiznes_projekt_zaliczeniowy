package com.example.routes.routes_external

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

fun Route.cartsRouting() {

    val notLoggedMessage = "Please log in to access your cart"

    route("cart") {
        authenticate("auth-session") {
            get("") {
                //returning Items
                val userSession = call.sessions.get<UserSession>() ?: return@get call.respondText(
                    notLoggedMessage,
                    status = HttpStatusCode.Forbidden
                )
                val cart = identityCheck(userSession)
                val items: List<ReturnShoppingCartItem> = daoCarts.getItems(cart.id)
                call.respond(items) //here is the issue
            }

            post("/add") {
                val userSession = call.sessions.get<UserSession>() ?: return@post call.respondText(
                    notLoggedMessage,
                    status = HttpStatusCode.Forbidden
                )
                val cart = identityCheck(userSession)
                val receivedItem = call.receive<TransferShoppingCartItem>()
                val itemInDB = daoCarts.getItem(cart.id, receivedItem.productId)
                if (itemInDB == null) {
                    daoCarts.addItem(cart.id, receivedItem.productId)
                    return@post call.respondText("Item received", status = HttpStatusCode.OK)
                }
                val quantity = itemInDB.quantity
                daoCarts.updateItem(cart.id, receivedItem.productId, quantity + 1)
                return@post call.respondText("Item updated", status = HttpStatusCode.OK)
            }

            post("/remove") {
                val userSession = call.sessions.get<UserSession>() ?: return@post call.respondText(
                    notLoggedMessage,
                    status = HttpStatusCode.Forbidden
                )
                val cart = identityCheck(userSession)
                val receivedItem = call.receive<TransferShoppingCartItem>()
                val itemInDB = daoCarts.getItem(cart.id, receivedItem.productId) ?: return@post call.respondText(
                    "Invalid call: Item not in database",
                    status = HttpStatusCode.NotFound
                )
                val quantity = itemInDB.quantity
                if (quantity > 1) {
                    daoCarts.updateItem(cart.id, receivedItem.productId, quantity - 1)
                    call.respondText("Item updated", status = HttpStatusCode.OK)
                } else {
                    daoCarts.removeItem(cart.id, receivedItem.productId)
                    call.respondText("Item removed from cart", status = HttpStatusCode.OK)
                }
            }
        }
    }
}


