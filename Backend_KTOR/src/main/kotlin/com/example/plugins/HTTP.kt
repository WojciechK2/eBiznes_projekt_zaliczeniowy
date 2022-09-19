package com.example.plugins

import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.application.*


fun Application.configureHTTP() {

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.Authorization)
        exposeHeader(HttpHeaders.Authorization)

        allowHeader(HttpHeaders.ContentType)
        allowCredentials = true

        allowHost("localhost:3000", schemes = listOf("https"))
        
        allowHeader("user_session")
        exposeHeader("user_session")
    }
}
