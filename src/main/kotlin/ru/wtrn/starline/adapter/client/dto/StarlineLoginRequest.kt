package ru.wtrn.starline.adapter.client.dto

data class StarlineLoginRequest(
    val username: String,
    val password: String,
    val rememberMe: Boolean = true
)