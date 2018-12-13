package com.airline.android

import android.app.Application
import android.content.Context
import com.airline.android.net.Credentials


/**
 * Main application class
 */
class App : Application() {
    private val preferences by lazy {
        getSharedPreferences(Const.PREFS_KEY, Context.MODE_PRIVATE)
    }

    /**
     * Loading credentials from shared preferences
     *
     * @return constructed object, filled with credentials, if found
     */
    fun loadCredentials(): Credentials {
        return if (preferences.getBoolean(Const.PREFS_LOGGED_FLAG, false)) {
            val authType = preferences.getString(Const.PREFS_AUTH_TYPE, "") ?: ""
            val sessionId = preferences.getString(Const.PREFS_SESSION_ID_KEY, "") ?: ""
            Credentials(authType, sessionId)
        } else {
            Credentials.empty()
        }
    }

    /**
     * Saving credentials to shared preferences
     *
     * @param credentials to save
     *
     * @return true if preference is wrote successfully
     */
    fun saveCredentials(credentials: Credentials): Boolean {
        with(preferences.edit()) {
            // if empty credentials passed, then user is logged out
            putBoolean(Const.PREFS_LOGGED_FLAG, !credentials.isEmpty())
            putString(Const.PREFS_AUTH_TYPE, credentials.authType)
            putString(Const.PREFS_SESSION_ID_KEY, credentials.sessionId)
            return commit()
        }
    }

    /**
     * Saving logged in user id to shared preferences
     *
     * @param userId to save
     *
     * @return true if preference is wrote successfully
     */
    fun saveUserId(userId: Int): Boolean {
        with(preferences.edit()) {
            putInt(Const.PREFS_USER_ID_KEY, userId)
            return commit()
        }
    }

    /**
     * Loading user id from preferences
     *
     * @return user id or 0, if not found
     */
    fun loadUserId() = preferences.getInt(Const.PREFS_USER_ID_KEY, 0)

    override fun onCreate() {
        super.onCreate()

        Const.credentials = this.loadCredentials()
    }
}
