package com.example.routes.routes_external

import com.example.dao.products.daoProducts
import com.example.models.Product
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.productsRouting() {
    route("products") {
        get("") {
            call.respond(daoProducts.getProducts())
        }

        route("/{id}") {
            get("") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val product: Product = daoProducts.getProduct(id)
                    ?: return@get call.respondText("No Product with this number", status = HttpStatusCode.NotFound)
                call.respond(product)
            }
        }
    }
}