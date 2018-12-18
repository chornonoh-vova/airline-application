package com.airline.android

import com.airline.android.model.Flight
import com.airline.android.model.Route
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.security.MessageDigest

val emailValidator = Regex("[a-zA-Z0-9]+@[a-z0-9]+\\.[a-z-0-9]+")
val passwordValidator = Regex("[a-zA-Z0-9]{6,}")

typealias RouteItemClick = (route: Route) -> Unit
typealias FlightItemClick = (flight: Flight) -> Unit

data class LoginRequest(
    val email: String,
    val password: String
)

data class AddPassengerRequest(
    val firstName: String,
    val lastName: String,
    val address: String,
    val phoneNumber: String
)

data class CheckTicketRequest(
    val seat: String
)

data class BuyTicketsRequest(
    val seats: List<String>
)

fun String.hash(): String {
    val bytes = this.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("") { str, it -> str + "%02x".format(it) }
}

fun ChipGroup.getAllChilds(): List<Chip> {
    val res = mutableListOf<Chip>()
    for (i in 0 until this.childCount) {
        res.add(this.getChildAt(i) as Chip)
    }
    return res
}
