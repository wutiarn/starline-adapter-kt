package ru.wtrn.starline.importer.dto

import java.time.Instant

data class RouteImportRequest(
    val deviceId: String? = null,
    val since: Instant,
    val until: Instant
)