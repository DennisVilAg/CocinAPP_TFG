package com.dennisdevs.cocinApp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("es", "ES"))
    return dateFormat.format(Date(timestamp))
}

fun formatTime(timestamp: Long): String {
    val timeFormat = SimpleDateFormat("HH:mm", Locale("es", "ES"))
    return timeFormat.format(Date(timestamp))
}