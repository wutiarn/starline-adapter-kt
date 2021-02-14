package ru.wtrn.starline.importer.controller

import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import ru.wtrn.starline.importer.client.StarlineClient
import ru.wtrn.starline.importer.client.StarlineDevice
import ru.wtrn.starline.importer.dto.RouteImportRequest

@RestController
@RequestMapping("/api/v1/status")
class StatusController(private val starlineClient: StarlineClient) {
    private val logger = KotlinLogging.logger {  }

    @GetMapping
    fun getStatus(): List<StarlineDevice> {
        return starlineClient.getDevices()
    }
}
