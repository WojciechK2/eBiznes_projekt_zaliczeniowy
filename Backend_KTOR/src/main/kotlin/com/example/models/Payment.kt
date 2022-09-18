package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class PaymentReturnIntent (
    val clientSecret: String
)

@Serializable
data class PaymentQuery (
    val orderId: Int,
)