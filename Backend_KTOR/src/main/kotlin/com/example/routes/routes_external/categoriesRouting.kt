package com.example.routes.routes_external

import com.example.dao.categories.daoCategories
import com.example.dao.products.daoProducts
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.categoriesRouting() {
    route("categories") {
        //get all categories names and references
        get("") {
            call.respond(daoCategories.getCategories())
        }
    }
    route("category/{id}"){
        get(""){
            val id = call.parameters.getOrFail("id").toInt()
            call.respond(daoProducts.getProductsByCategory(id))
        }
    }
}