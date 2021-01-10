package ru.wtrn.starline.importer.client

import mu.KotlinLogging
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.wtrn.starline.importer.configuration.properties.StarlineApiProperties
import java.time.Instant

@Component
class StarlineClientImpl(private val properties: StarlineApiProperties) : StarlineClient {
    private val httpClient: RestTemplate
    private val logger = KotlinLogging.logger {  }

    init {
        val authInterceptor = StarlineClientAuthInterceptor()
        httpClient = RestTemplateBuilder()
            .rootUri(properties.baseUrl)
            .interceptors(authInterceptor)
            .build()
    }

    override fun getDevices(): List<StarlineDevice> {
        val response = httpClient.getForEntity("/device", String::class.java)
        logger.info { "Devices response: ${response.body}" }
        return emptyList()
    }

    override fun getRoute(deviceId: String, since: Instant, until: Instant): StarlineRoute {
        TODO("Not yet implemented")
    }
}