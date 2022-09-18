package com.example

import com.example.dao.DatabaseFactory
import io.ktor.server.application.*
import com.example.plugins.*
import com.stripe.Stripe

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    DatabaseFactory.init()
    Stripe.apiKey = System.getenv("STRIPE_API_SECRET_KEY")
    configureHTTP()
    configureSecurity()
    configureSerialization()
    configureRouting()
}
