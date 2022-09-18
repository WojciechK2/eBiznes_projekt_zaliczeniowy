package com.example.utils

import com.example.dao.shoppingcart.daoCarts
import com.example.httpClient
import com.example.models.GithubUserInfo
import com.example.models.GoogleUserInfo
import com.example.models.ShoppingCart
import com.example.plugins.UserSession
import io.ktor.http.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking

fun identityCheck(session: UserSession): ShoppingCart {
    val cart : ShoppingCart
    if(session.provider.equals("google")) {
        runBlocking {
            val userInfo: GoogleUserInfo = httpClient.get("https://www.googleapis.com/oauth2/v2/userinfo") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${session.token}")
                }
            }.body()
            cart = ((daoCarts.getCart(userInfo.id) ?: daoCarts.addCart(userInfo.id)) as ShoppingCart)
            println(userInfo.id)
        }
    } else {
        runBlocking {
            val userInfo: GithubUserInfo = httpClient.get("https://api.github.com/user") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${session.token}")
                }
            }.body()
            cart =
                ((daoCarts.getCart(userInfo.id.toString()) ?: daoCarts.addCart(userInfo.id.toString())) as ShoppingCart)
            println(userInfo.id)
        }
    }
    return cart
}