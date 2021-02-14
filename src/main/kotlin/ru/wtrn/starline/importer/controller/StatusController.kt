package ru.wtrn.starline.importer.controller

import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import ru.wtrn.starline.importer.client.StarlineClient
import ru.wtrn.starline.importer.client.StarlineDevice
import ru.wtrn.starline.importer.dto.RouteImportRequest
import ru.wtrn.starline.importer.dto.StarlineDeviceStatus
import java.lang.IllegalStateException

@RestController
@RequestMapping("/api/v1/status")
class StatusController(private val starlineClient: StarlineClient) {
    private val logger = KotlinLogging.logger {  }

    @GetMapping("/{deviceId}")
    fun getStatus(@PathVariable deviceId: String): StarlineDeviceStatus {
        val device = starlineClient.getDevices().firstOrNull { it.deviceId == deviceId }
            ?: throw IllegalStateException("Cannot find device with requested id")
        val canInfo = starlineClient.getCanInfo(deviceId)
        return StarlineDeviceStatus(
            alias = device.alias,
            engineTemp = device.engineTemp,
            interiorTemp = device.interiorTemp,
            mileage = canInfo.mileage,
            activityTimestamp = canInfo.timestamp
        )
    }
}
