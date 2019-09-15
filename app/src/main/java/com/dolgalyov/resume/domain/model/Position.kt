package com.dolgalyov.resume.domain.model

import java.util.*

data class Position(
    val id: String,
    val name: String,
    val company: Company,
    val description: String,
    val from: Date,
    val to: Date?
)