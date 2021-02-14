package ru.wtrn.starline.adapter.client.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.Duration
import java.time.Instant

data class StarlineRouteResponse(
    val meta: RouteMeta,
    val data: List<RouteDataNode>
) {
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
    data class RouteMeta(
        val mileage: Long,
        val movingTime: Int,
        val waitingTime: Int
    )

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
    data class RouteDataNode(
        val type: String,
        val x: Float?,
        val y: Float?,
        val t: Long?,
        val satQty: Int?,
        val movingTime: Int?,
        val waitingTime: Int?,
        val mileage: Long?,
        val nodes: List<RouteTrackNode>?
    )

    data class RouteTrackNode(
        val x: Float,
        val y: Float,
        val t: Long,
        val s: Int,
        val satQty: Int,
        val mileage: Long
    )
}
