package com.example.plugins

import com.example.routes.routes_admin.adminPathsCategories
import com.example.routes.routes_admin.adminPathsLogin
import com.example.routes.routes_admin.adminPathsProducts
import com.example.routes.routes_external.*
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        cartsRouting()
        categoriesRouting()
        checkLoginRoute()
        oAuthRouting()
        ordersRouting()
        paymentsRouting()
        productsRouting()
        adminPathsProducts()
        adminPathsCategories()
        adminPathsLogin()
    }
}
