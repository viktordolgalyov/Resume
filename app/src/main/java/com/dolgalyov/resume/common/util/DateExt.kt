package com.dolgalyov.resume.common.util

import java.text.SimpleDateFormat
import java.util.*

fun String.toDateOrNull(): Date? {
    return getOrNull { SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(this) }
}

fun Date.formatToString(): String {
    return getOrNull { SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(this) }.orEmpty()
}

fun Date.formatShortDate(): String {
    return getOrNull { SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(this) }.orEmpty()
}