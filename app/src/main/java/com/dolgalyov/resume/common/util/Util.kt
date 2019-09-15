package com.dolgalyov.resume.common.util

inline fun <T> getOrNull(body: () -> T): T? = try {
    body()
} catch (e: Exception) {
    null
}