package ru.wtrn.starline.importer.client

import mu.KotlinLogging
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.client.support.HttpRequestWrapper
import ru.wtrn.starline.importer.client.dto.StarlineLoginRequest
import ru.wtrn.starline.importer.client.exception.AuthenticationFailedException
import ru.wtrn.starline.importer.configuration.properties.StarlineApiProperties
import java.io.File
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class StarlineAuthHolder(private val apiProperties: StarlineApiProperties) {
    private val httpClient = RestTemplateBuilder().rootUri(apiProperties.baseUrl).build()
    private val logger = KotlinLogging.logger { }

    private var cookieHeader: String? = apiProperties.authCacheLocation?.let {
        File(apiProperties.authCacheLocation).takeIf { it.exists() }?.readText()
    }
    private val cookieHeaderLock = ReentrantLock()

    fun addAuthenticationToRequest(httpRequest: HttpRequest): HttpRequest {
        val wrapper = HttpRequestWrapper(httpRequest)
        wrapper.headers.set(HttpHeaders.COOKIE, getCookieHeader())
        return wrapper
    }

    fun refreshAuthentication() {
        doRefreshAuthentication()
    }

    private fun getCookieHeader(): String {
        cookieHeaderLock.withLock {
            return cookieHeader ?: doRefreshAuthentication()
        }
    }

    private fun doRefreshAuthentication(): String {
        val request = StarlineLoginRequest(
            username = apiProperties.username,
            password = apiProperties.password
        )
        val response = httpClient.postForEntity("/rest/security/login", request, String::class.java)
        if (response.statusCode != HttpStatus.NO_CONTENT) {
            throw AuthenticationFailedException(response.body)
        }

        val cookies = response.headers[HttpHeaders.SET_COOKIE]
            ?: throw IllegalStateException("Successful login request did not return Set-Cookie headers")
        val cookieHeader = composeCookieHeader(cookies)
        logger.info { "Starline authentication refreshed" }

        if (apiProperties.authCacheLocation != null) {
            val file = File(apiProperties.authCacheLocation)
            file.writeText(cookieHeader)
        }

        return cookieHeader
    }

    private fun composeCookieHeader(setCookieValues: List<String>): String {
        return setCookieValues.map { it.split(";").first() }.joinToString(separator = "; ")
    }
}