package ru.wtrn.starline.importer.client

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse

class StarlineClientAuthInterceptor : ClientHttpRequestInterceptor {
    private val httpClient = RestTemplateBuilder().build()

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        return execution.execute(request, body)
    }
}