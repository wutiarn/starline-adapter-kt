package ru.wtrn.starline.adapter.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("ru.wtrn.starline.adapter.api")
data class StarlineApiProperties(
    val baseUrl: String,
    val username: String,
    val password: String,
    val authCacheLocation: String? = "auth.txt"
)