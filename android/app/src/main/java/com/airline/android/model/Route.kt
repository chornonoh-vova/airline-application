package com.airline.android.model

import java.io.Serializable

/**
 * Class, that represents route
 */
data class Route(
    val routeId: Int,
    val departureAirport: String,
    val destinationAirport: String,
    val price: Double,
    val duration: String
) : Serializable, Data
