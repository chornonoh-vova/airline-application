package com.airline.android

import com.airline.android.net.Credentials

/**
 * Class for centralized storing of preference keys
 */
object Const {
    // preference file name
    const val PREFS_KEY = "airline_application_shared_prefs"

    // to determine, if user is logged in
    const val PREFS_LOGGED_FLAG = "IS_USER_LOGGED_IN"

    // user id for profile
    const val PREFS_USER_ID_KEY = "USER_ID"

    // credentials for API authorization
    const val PREFS_SESSION_ID_KEY = "SESSION_ID"
    const val PREFS_AUTH_TYPE = "Basic"

    lateinit var credentials: Credentials
}