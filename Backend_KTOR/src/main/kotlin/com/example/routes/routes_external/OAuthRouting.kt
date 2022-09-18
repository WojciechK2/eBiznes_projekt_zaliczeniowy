package com.example.routes.routes_external

import com.example.plugins.UserSession
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.oAuthRouting() {
    route("") {
        authenticate("auth-oauth-google") {
            get("/login") {
                //automatic redirection to 'authorizeURL'
            }

            get("/callback") {
                val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
                call.sessions.set(UserSession(principal?.accessToken.toString(),"google"))
                call.respondRedirect(System.getenv("FRONTEND_SERVER_URL"))
            }
        }
        get("/logout") {
            call.sessions.clear<UserSession>()
            call.respondRedirect(System.getenv("FRONTEND_SERVER_URL"))
        }
        authenticate("auth-oauth-github"){
            get("/login_github"){
                call.respondRedirect(System.getenv("FRONTEND_SERVER_URL"))
            }
            get("/callback_github"){
                val principal: OAuthAccessTokenResponse.OAuth2? = call.authentication.principal()
                call.sessions.set(UserSession(principal?.accessToken.toString(),"github"))
                call.respondRedirect(System.getenv("FRONTEND_SERVER_URL"))
            }
        }
        get("/logout") {
            call.sessions.clear<UserSession>()
            call.respondRedirect(System.getenv("FRONTEND_SERVER_URL"))
        }
    }
}