package ru.wtrn.starline.adapter.dto

import java.time.ZonedDateTime

data class RouteRequest(
    val deviceId: String? = null,
    val since: ZonedDateTime,
    val until: ZonedDateTime
)