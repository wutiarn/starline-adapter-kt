package ru.wtrn.starline.adapter.dto

import java.time.Instant

data class StarlineDeviceStatus(
    val alias: String,
    val engineTemp: Int,
    val interiorTemp: Int,
    val mileage: Int,
    val activityTimestamp: Instant
)