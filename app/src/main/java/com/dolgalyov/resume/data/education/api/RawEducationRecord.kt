package com.dolgalyov.resume.data.education.api

import com.google.gson.annotations.SerializedName

data class RawEducationRecord(
    val id: String,
    @SerializedName("university_id") val universityId: String,
    @SerializedName("from_date") val fromDate: String,
    @SerializedName("to_date") val toDate: String,
    val degree: String
)