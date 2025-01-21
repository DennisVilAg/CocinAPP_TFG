package com.dennisdevs.cocinApp.utils

import android.util.Log

object Logger {
    fun logError(message: String, exception: Throwable) {
        Log.e("AppTag", "$message: ${exception.message}", exception)
    }
}