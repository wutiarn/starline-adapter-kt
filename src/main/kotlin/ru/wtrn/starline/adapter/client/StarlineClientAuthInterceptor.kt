package ru.wtrn.starline.adapter.client

import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import ru.wtrn.starline.adapter.configuration.properties.StarlineApiProperties

class StarlineClientAuthInterceptor(private val apiProperties: StarlineApiProperties) : ClientHttpRequestInterceptor {

    private val authHolder = StarlineAuthHolder(apiProperties)

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        val wrappedRequest = authHolder.addAuthenticationToRequest(request)
        val response = execution.execute(wrappedRequest, body)

        if (checkResponseRequiresAuthenticationRefresh(response)) {
            return retryRequestWithRefreshedCredentials(request, body, execution)
        }

        return response
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
        authHolder.refreshAuthentication()
        val wrappedRequest = authHolder.addAuthenticationToRequest(request)
        return execution.execute(wrappedRequest, body)
    }
}