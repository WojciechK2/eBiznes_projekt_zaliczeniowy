package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import java.io.File

data class UserSession(val token:String, val provider:String) : Principal

fun Application.configureSessions() {
    install(Sessions) {
        val secretEncryptKey = "00112233445566778899aabbccddeeff"
        val secretSignKey = "6819b57a326945c1968f45236589"
        cookie<UserSession>(
            "user_session",
            directorySessionStorage(File("build/.sessions"))
        ){
            cookie.path = "/"
            cookie.maxAgeInSeconds = 3600
            cookie.httpOnly = false
            transform(SessionTransportTransformerEncrypt(hex(secretEncryptKey), hex(secretSignKey)))
        }
    }
}