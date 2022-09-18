package com.example.routes.routes_admin

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.models.AdminUser
import com.example.models.adminUserTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.adminPathsLogin() {
    //endpoint for creating tokens after proper authorization (username/password)
    route("/admin") {
        post("/login") {
            val user = call.receive<AdminUser>()

            //check the credentials
            val userRecord = adminUserTable.find { it.username == user.username } ?: return@post call.respondText(
                "Invalid credentials",
                status = HttpStatusCode.NotFound
            )
            if (userRecord.password == user.password) {

                val jwtAudience = this@adminPathsLogin.environment!!.config.property("jwt.audience").getString()
                val issuer = this@adminPathsLogin.environment!!.config.property("jwt.domain").getString()
                val secret = System.getenv("SECRET")

                val token = JWT.create()
                    .withAudience(jwtAudience)
                    .withIssuer(issuer)
                    .withClaim("username", user.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 20 * 60 * 1000))
                    .sign(Algorithm.HMAC256(secret))
                call.respond(hashMapOf("token" to token))
            } else {
                return@post call.respondText(
                    "Invalid credentials",
                    status = HttpStatusCode.NotFound
                )
            }
        }
    }
}