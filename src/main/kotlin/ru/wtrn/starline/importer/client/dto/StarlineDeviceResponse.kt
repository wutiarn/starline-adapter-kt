package ru.wtrn.starline.importer.client.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

data class StarlineDeviceResponse(
    val answer: Answer
) {
    data class Answer(
        val devices: List<Device>
    )

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
    data class Device(
        val alias: String,
        val deviceId: String,
        val ctemp: Int,
        val etemp: Int
    )
}