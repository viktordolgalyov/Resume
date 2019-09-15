package com.dolgalyov.resume.data.education.model

import java.util.*

data class EducationRecordDto(
    val id: String,
    val universityId: String,
    val from: Date,
    val to: Date,
    val degree: String
)