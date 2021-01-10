package ru.wtrn.starline.importer.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("ru.wtrn.starline.importer.api")
data class StarlineApiProperties(
    val baseUrl: String,
    val username: String,
    val password: String
)