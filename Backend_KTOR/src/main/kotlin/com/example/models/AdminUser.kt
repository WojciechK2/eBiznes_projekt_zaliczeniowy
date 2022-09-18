package com.example.models

import kotlinx.serialization.Serializable

val username: String = System.getenv("ADMIN_USERNAME")
val password: String = System.getenv("ADMIN_PASSWORD")

@Serializable
data class AdminUser(val username: String, val password: String)

val adminUserTable = listOf<AdminUser>(
    AdminUser(username, password)
)