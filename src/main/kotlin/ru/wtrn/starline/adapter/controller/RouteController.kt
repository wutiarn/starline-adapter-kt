package ru.wtrn.starline.adapter.controller

import mu.KotlinLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.wtrn.starline.adapter.client.StarlineClient
import ru.wtrn.starline.adapter.client.StarlineRoute
import ru.wtrn.starline.adapter.dto.RouteRequest

@RestController
@RequestMapping("/api/v1/route")
class RouteController(private val starlineClient: StarlineClient) {
    private val logger = KotlinLogging.logger {  }

    @PostMapping
    fun importRoute(@RequestBody request: RouteRequest): StarlineRoute {
        logger.info { "Handling request: $request" }
        return starlineClient.getRoute(
            deviceId = request.deviceId,
            since = request.since.toInstant(),
            until = request.until.toInstant()
        )
    }
}
