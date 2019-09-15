package com.dolgalyov.resume.data.position.api

import com.google.gson.annotations.SerializedName

data class RawPosition(
    val id: String,
    val name: String,
    @SerializedName("company_id") val companyId: String,
    val description: String,
    @SerializedName("from_date") val fromDate: String,
    @SerializedName("to_date") val toDate: String?
)