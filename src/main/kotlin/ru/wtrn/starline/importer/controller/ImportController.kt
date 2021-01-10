package ru.wtrn.starline.importer.controller

import mu.KotlinLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.wtrn.starline.importer.client.StarlineClient
import ru.wtrn.starline.importer.dto.RouteImportRequest

@RestController
@RequestMapping("/api/v1/import")
class ImportController(private val starlineClient: StarlineClient) {
    private val logger = KotlinLogging.logger {  }

    @PostMapping("route")
    fun importRoute(@RequestBody request: RouteImportRequest) {
        logger.info { "Handling request: $request" }
        starlineClient.getDevices()
    }
}
