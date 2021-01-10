package ru.wtrn.starline.importer.client

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.http.client.support.HttpRequestWrapper
import ru.wtrn.starline.importer.client.dto.StarlineLoginRequest
import ru.wtrn.starline.importer.configuration.properties.StarlineApiProperties

class StarlineClientAuthInterceptor(private val apiProperties: StarlineApiProperties) : ClientHttpRequestInterceptor {

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        val wrappedRequest = addAuthHeadersToRequest(request)
        val response = execution.execute(wrappedRequest, body)

        if (checkResponseRequiresAuthenticationRefresh(response)) {
            return retryRequestWithRefreshedCredentials(request, body, execution)
        }

        return response
    }

    private fun addAuthHeadersToRequest(httpRequest: HttpRequest): HttpRequest {
        val wrapper = HttpRequestWrapper(httpRequest)
        return wrapper
    }

    private fun checkResponseRequiresAuthenticationRefresh(response: ClientHttpResponse): Boolean {
        when (response.statusCode) {
            HttpStatus.TEMPORARY_REDIRECT,
            HttpStatus.UNAUTHORIZED,
            HttpStatus.FORBIDDEN -> return true
            else -> Unit
        }

        val contentType = response.headers.contentType
        if (contentType?.includes(MediaType.APPLICATION_JSON) != true) {
            return true
        }

        return false
    }

    private fun retryRequestWithRefreshedCredentials(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        val wrappedRequest = addAuthHeadersToRequest(request)
        return execution.execute(wrappedRequest, body)
    }
}