package ru.wtrn.starline.adapter.client

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
    val tracks: List<RouteTrack>,
    val stops: List<RouteStop>
) {
    data class RouteMeta(
        val mileage: Long,
        val movingTime: Duration,
        val waitingTime: Duration
    )

    data class RouteTrack(
        val timestamp: Instant,
        val mileage: Long,
        val movingTime: Duration,
        val waitingTime: Duration,
        val nodes: List<RouteTrackNode>
    )

    data class RouteTrackNode(
        val x: Float,
        val y: Float,
        val speed: Int,
        val timestamp: Instant,
        val satQty: Int,
        val mileage: Long
    )

    data class RouteStop(
        val x: Float,
        val y: Float,
        val timestamp: Instant,
        val satQty: Int,
        val waitingTime: Duration
    )
}