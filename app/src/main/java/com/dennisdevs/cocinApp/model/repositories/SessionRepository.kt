package com.dennisdevs.cocinApp.model.repositories

import android.content.Context
import com.dennisdevs.cocinApp.model.data_sources.SessionManager

class SessionRepository(private val context: Context) {
    fun setLoggedIn(isLoggedIn: Boolean) {
        SessionManager.setLoggedIn(context, isLoggedIn)
    }

    fun setUserId(userId: String) {
        SessionManager.setUserId(context, userId)
    }
}