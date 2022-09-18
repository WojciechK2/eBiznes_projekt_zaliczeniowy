package com.example.routes.routes_external

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.checkLoginRoute(){
    route("/checklogin"){
        //Endpoint which react app uses to check if the session is established
        authenticate("check-login"){
            get(""){
                call.respondText("true")
            }
        }
    }
}