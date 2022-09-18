package com.example.routes.routes_external

import com.example.dao.orders.daoOrders
import com.example.models.PaymentQuery
import com.example.models.PaymentReturnIntent
import com.example.plugins.UserSession
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*


fun Route.paymentsRouting(){
    authenticate("auth-session") {
        route("/payment"){
               post("/create_intent") {

                call.sessions.get<UserSession>() ?: return@post call.respondText(
                    "Please log in to access your cart",
                    status = HttpStatusCode.Forbidden
                )

                val orderId = call.receive<PaymentQuery>()
                val totalPrice = daoOrders.getOrderByOrderId(orderId.orderId) ?: return@post call.respondText(
                    "Database Error",
                    status = HttpStatusCode.InternalServerError
                )

                val params: PaymentIntentCreateParams = PaymentIntentCreateParams.builder()
                    .setAmount((totalPrice.totalPrice * 100).toLong())
                    .setCurrency("pln")
                    .addPaymentMethodType("card")
                    .build()

                val paymentIntent: PaymentIntent = PaymentIntent.create(params)

                val returnIntent = PaymentReturnIntent(paymentIntent.clientSecret)

                call.respond(returnIntent)
            }
        }
    }
}