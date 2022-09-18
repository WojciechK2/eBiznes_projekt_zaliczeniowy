package com.example.routes.routes_admin

import com.example.dao.products.daoProducts
import com.example.models.TransferProduct
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.adminPathsProducts() {
    authenticate("admin-auth-jwt") {
        route("/admin/products") {
            post("/add") {
                val transferProduct = call.receive<TransferProduct>()

                daoProducts.addProduct(
                    transferProduct.name,
                    transferProduct.description,
                    transferProduct.price,
                    transferProduct.categoryReference
                ) ?: return@post call.respondText("Product not added", status = HttpStatusCode.BadRequest)

                call.respondText(
                    "Product created",
                    status = HttpStatusCode.Accepted
                )

            }
            route("/{id}") {

                put("") {
                    val id = call.parameters.getOrFail<Int>("id").toInt()
                    val transferProduct = call.receive<TransferProduct>()
                    val success = daoProducts.editProduct(
                        id,
                        transferProduct.name,
                        transferProduct.description,
                        transferProduct.price,
                        transferProduct.categoryReference
                    )
                    if (success) {
                        call.respondText(
                            "Product edited",
                            status = HttpStatusCode.Accepted
                        )
                    } else {
                        call.respondText(
                            "Product not edited",
                            status = HttpStatusCode.NotFound
                        )
                    }
                }

                delete("") {
                    val id = call.parameters.getOrFail<Int>("id").toInt()
                    val sucess = daoProducts.deleteProduct(id)
                    if (sucess) {
                        call.respondText(
                            "Product deleted",
                            status = HttpStatusCode.Accepted
                        )
                    } else {
                        call.respondText(
                            "No Product deletion happened",
                            status = HttpStatusCode.NotFound
                        )
                    }
                }
            }
        }
    }
}