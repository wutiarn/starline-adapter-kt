package ru.wtrn.starline.adapter.client

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.wtrn.starline.adapter.client.dto.StarlineDeviceResponse
import ru.wtrn.starline.adapter.client.dto.StarlineRouteResponse
import ru.wtrn.starline.adapter.configuration.properties.StarlineApiProperties
import java.net.URL
import java.net.URLEncoder
import java.time.Instant

@Component
class StarlineClientImpl(
    properties: StarlineApiProperties,
    private val objectMapper: ObjectMapper
) : StarlineClient {
    private val httpClient: RestTemplate
    private val logger = KotlinLogging.logger { }

    init {
        val authInterceptor = StarlineClientAuthInterceptor(properties)
        httpClient = RestTemplateBuilder()
            .rootUri(properties.baseUrl)
            .interceptors(authInterceptor)
            .build()
    }

    override fun getDevices(): List<StarlineDevice> {
        val response = httpClient.getForEntity("/device", String::class.java)
        logger.debug { "Devices response: ${response.body}" }

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

    override fun getCanInfo(deviceId: String): CanInfo {
        val response =
            httpClient.getForEntity("/deviceSettings/settings?deviceId=$deviceId&formId=can", String::class.java)
        logger.debug { "CAN info response: ${response.body}" }

        val mileageNode = objectMapper.readTree(response.body).get("desc").get("mileage")
        return CanInfo(
            mileage = mileageNode.get("val").asInt(),
            timestamp = mileageNode.get("ts").let { Instant.ofEpochSecond(it.asLong()) }
        )
    }

    override fun getRoute(deviceId: String, since: Instant, until: Instant): StarlineRoute {
        val url = UriComponentsBuilder.fromPath("/device/$deviceId/route")
            .queryParam("beginTime", since.epochSecond)
            .queryParam("endTime", until.epochSecond)
            .queryParam("timezoneOffset", 0)
            .queryParam("shortParkingTime", 5)
            .queryParam("longParkingTime", 30)
            .build()
            .toString()
        val response = httpClient.getForEntity(url, String::class.java)
        logger.debug { "Route response: ${response.body}" }

        val responseDto = objectMapper.readValue(response.body, StarlineRouteResponse::class.java)

        TODO("Not yet implemented")
    }
}