package ru.wtrn.starline.importer.client

import java.time.Duration
import java.time.Instant

interface StarlineClient {
    fun getDevices(): List<StarlineDevice>
    fun getCanInfo(deviceId: String): CanInfo
    fun getRoute(deviceId: String, since: Instant, until: Instant): StarlineRoute
}

data class StarlineDevice(
    val alias: String,
    val deviceId: String,
    val engineTemp: Int,
    val interiorTemp: Int
)

data class CanInfo(
    val mileage: Int,
    val timestamp: Instant
)

data class StarlineRoute(
    val meta: RouteMeta,
    val data: List<RouteDataNode>
)

data class RouteMeta(
    val mileage: Long,
    val movingTime: Duration,
    val waitingTime: Duration
)

data class RouteDataNode(
    val x: Float,
    val y: Float,
    val timestamp: Instant,
    val satQty: Int,
    val movingTime: Duration,
    val waitingTime: Duration,
    val mileage: Long,
    val nodes: List<RouteTrackNode>
)

data class RouteTrackNode(
    val x: Float,
    val y: Float,
    val timestamp: Instant,
    val satQty: Int,
    val mileage: Long
)