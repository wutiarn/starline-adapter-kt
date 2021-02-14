package ru.wtrn.starline.importer.client

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.wtrn.starline.importer.client.dto.StarlineDeviceResponse
import ru.wtrn.starline.importer.configuration.properties.StarlineApiProperties
import java.time.Instant

@Component
class StarlineClientImpl(
    properties: StarlineApiProperties,
    private val objectMapper: ObjectMapper
) : StarlineClient {
    private val httpClient: RestTemplate
    private val logger = KotlinLogging.logger {  }

    init {
        val authInterceptor = StarlineClientAuthInterceptor(properties)
        httpClient = RestTemplateBuilder()
            .rootUri(properties.baseUrl)
            .interceptors(authInterceptor)
            .build()
    }

    override fun getDevices(): List<StarlineDevice> {
        val response = httpClient.getForEntity("/device", String::class.java)
        logger.info { "Devices response: ${response.body}" }

        val responseDto = objectMapper.readValue(response.body, StarlineDeviceResponse::class.java)
        return responseDto.answer.devices.map {
            StarlineDevice(
                alias = it.alias,
                deviceId = it.deviceId,
                engineTemp = it.etemp,
                interiorTemp = it.ctemp
            )
        }
    }

    override fun getRoute(deviceId: String, since: Instant, until: Instant): StarlineRoute {
        TODO("Not yet implemented")
    }
}