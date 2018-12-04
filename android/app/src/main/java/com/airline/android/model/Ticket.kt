package com.airline.android.model

import java.io.Serializable

/**
 * Class, that represents bought ticket
 */
data class Ticket(
    val ticketId: Int,
    val flightId: Int,
    val passengerId: Int,
    val seat: String
) : Serializable, Data
