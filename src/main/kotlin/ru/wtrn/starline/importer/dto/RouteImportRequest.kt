package ru.wtrn.starline.importer.dto

import java.time.Instant
import java.time.LocalDateTime

data class RouteImportRequest(
    val deviceId: String? = null,
    val since: LocalDateTime,
    val until: LocalDateTime
)