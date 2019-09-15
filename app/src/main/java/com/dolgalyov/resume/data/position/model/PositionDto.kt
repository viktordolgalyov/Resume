package com.dolgalyov.resume.data.position.model

import java.util.*

data class PositionDto(
    val id: String,
    val name: String,
    val companyId: String,
    val description: String,
    val from: Date,
    val to: Date?
)