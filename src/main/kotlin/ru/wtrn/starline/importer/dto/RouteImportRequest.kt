package ru.wtrn.starline.importer.dto

import java.time.Instant
import java.time.ZonedDateTime

data class RouteImportRequest(
    val deviceId: String? = null,
    val since: ZonedDateTime,
    val until: ZonedDateTime
)