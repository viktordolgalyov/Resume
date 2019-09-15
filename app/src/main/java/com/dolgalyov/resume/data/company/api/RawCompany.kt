package com.dolgalyov.resume.data.company.api

import com.dolgalyov.resume.data.address.RawAddress

data class RawCompany(
    val id: String,
    val name: String,
    val address: RawAddress
)