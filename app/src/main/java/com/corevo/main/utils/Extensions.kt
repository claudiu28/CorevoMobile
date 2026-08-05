package com.corevo.main.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String?.orDefault(default: String = "N/A"): String = this ?: default

fun String?.formatDate(): String {
    if (this.isNullOrBlank()) return ""
    return try {
        if (this.length >= 10) this.substring(0, 10) else this
    } catch (e: Exception) {
        this
    }
}

fun Long.toReadableDate(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}
