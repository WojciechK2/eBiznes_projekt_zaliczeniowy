package com.example.plugins

import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.application.*


fun Application.configureHTTP() {

    install(CORS) {
        allowHeader(HttpHeaders.ContentType)
        allowCredentials = true

        allowHost("localhost:3000")
    }
}
