package ru.wtrn.starline.importer.client.dto

data class StarlineLoginRequest(
    val username: String,
    val password: String,
    val rememberMe: Boolean = true
)