package com.airline.android.model

import java.io.Serializable

/**
 * Class, that represents flight
 */
data class Flight(
    val flightId: Int,
    val departureTime: String,
    val arrivalTime: String,
    val cancellationStatus: Boolean,
    val routeId: Int,
    val pilotId: Int,
    val airplaneId: Int
) : Serializable