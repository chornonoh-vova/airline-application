package com.airline.android

import java.security.MessageDigest

val emailValidator = Regex("[a-zA-Z0-9]+@[a-z0-9]+\\.[a-z-0-9]+")
val passwordValidator = Regex("[a-zA-Z0-9]{6,}")

data class LoginRequest(
    val email: String,
    val password: String
)

data class AddPassengerRequest (
    val firstName: String,
    val lastName: String,
    val address: String,
    val phone: String
)

fun String.hash(): String {
    val bytes = this.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("") { str, it -> str + "%02x".format(it) }
}
