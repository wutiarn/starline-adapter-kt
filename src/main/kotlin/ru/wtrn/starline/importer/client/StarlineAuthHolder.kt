package ru.wtrn.starline.importer.client

import mu.KotlinLogging
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.client.support.HttpRequestWrapper
import ru.wtrn.starline.importer.client.dto.StarlineLoginRequest
import ru.wtrn.starline.importer.client.exception.AuthenticationFailedException
import ru.wtrn.starline.importer.configuration.properties.StarlineApiProperties

class StarlineAuthHolder(private val apiProperties: StarlineApiProperties) {
    private val httpClient = RestTemplateBuilder().rootUri(apiProperties.baseUrl).build()
    private val logger = KotlinLogging.logger { }

    fun addAuthenticationToRequest(httpRequest: HttpRequest): HttpRequest {
        val wrapper = HttpRequestWrapper(httpRequest)

        return wrapper
    }

    fun refreshAuthentication() {
        val request = StarlineLoginRequest(
            username = apiProperties.username,
            password = apiProperties.password
        )
        val response = httpClient.postForEntity("/rest/security/login", request, String::class.java)
        if (response.statusCode != HttpStatus.NO_CONTENT) {
            throw AuthenticationFailedException(response.body)
        }

        val cookies = response.headers["Set-Cookie"]
            ?: throw IllegalStateException("Successful login request did not return Set-Cookie headers")
        val cookieHeader = composeCookieHeader(cookies)

        logger.info { "Starline authentication refreshed" }
    }

    private fun composeCookieHeader(setCookieValues: List<String>): String {
        return setCookieValues.map { it.split(";").first() }.joinToString(separator = "; ")
    }
}