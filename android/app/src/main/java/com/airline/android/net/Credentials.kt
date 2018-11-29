package com.airline.android.net

import java.io.Serializable

/**
 * POJO class to store user's credentials(type and session id).
 *
 * @sample "Basic 944ce7e3-e4d6-4aa7-b4cd-5aaa96bb2dde"
 */
data class Credentials(
    val authType: String,
    val sessionId: String
) : Serializable {
    /**
     * Helper function to determine empty credentials
     */
    fun isEmpty() = authType.isBlank() && sessionId.isBlank()

    /**
     * Returns properly formatted Authorization field
     */
    override fun toString() = "$authType $sessionId"

    companion object {
        fun empty() = Credentials("", "")
    }
}