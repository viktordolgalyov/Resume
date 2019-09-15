package com.dolgalyov.resume.domain.model

import java.util.*

data class EducationRecord(
    val id: String,
    val university: University,
    val degree: String,
    val from: Date,
    val to: Date
)