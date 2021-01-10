package ru.wtrn.starline.importer.client

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.RestTemplate
import ru.wtrn.starline.importer.configuration.properties.StarlineApiProperties
import java.time.Instant

class StarlineClientImpl(private val properties: StarlineApiProperties) : StarlineClient {
    private val httpClient: RestTemplate

    init {
        val authInterceptor = StarlineClientAuthInterceptor()
        httpClient = RestTemplateBuilder()
            .interceptors(authInterceptor)
            .build()
    }

    override fun getDevices(): List<StarlineDevice> {
        TODO("Not yet implemented")
    }

    override fun getRoute(deviceId: String, since: Instant, until: Instant): StarlineRoute {
        TODO("Not yet implemented")
    }
}