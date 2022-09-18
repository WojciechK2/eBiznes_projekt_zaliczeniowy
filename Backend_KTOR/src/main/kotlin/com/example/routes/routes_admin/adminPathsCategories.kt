package com.example.routes.routes_admin

import com.example.dao.categories.daoCategories
import com.example.models.Category
import com.example.models.TransferCategory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.adminPathsCategories(){
    authenticate("admin-auth-jwt"){
        route("/admin/categories"){
            post("/add") {
                val transferCategory = call.receive<TransferCategory>()
                daoCategories.addCategory(transferCategory.name)
                    ?: return@post call.respondText("Category not added", status = HttpStatusCode.BadRequest)
                call.respondText(
                    "Category created",
                    status = HttpStatusCode.Accepted
                )
            }

            route("/{id}") {

                get("") {
                    val id = call.parameters.getOrFail<Int>("id").toInt()
                    val category: Category = daoCategories.getCategory(id)
                        ?: return@get call.respondText("No category with this number", status = HttpStatusCode.NotFound)
                    call.respond(category)
                }

                put("") {
                    val id = call.parameters.getOrFail<Int>("id").toInt()
                    val transferCategory = call.receive<TransferCategory>()
                    val success = daoCategories.editCategory(id, transferCategory.name)
                    if (success) {
                        call.respondText(
                            "Category edited",
                            status = HttpStatusCode.Accepted
                        )
                    } else {
                        call.respondText(
                            "Category not edited",
                            status = HttpStatusCode.NotFound
                        )
                    }
                }

                delete("") {
                    val id = call.parameters.getOrFail<Int>("id").toInt()
                    val sucess = daoCategories.deleteCategory(id)
                    if (sucess) {
                        call.respondText(
                            "Article deleted",
                            status = HttpStatusCode.Accepted
                        )
                    } else {
                        call.respondText(
                            "No deletion happened",
                            status = HttpStatusCode.NotFound
                        )
                    }
                }
            }
        }
    }
}