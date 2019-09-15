package com.dolgalyov.resume.data.company.model

import com.dolgalyov.resume.data.address.Address

data class CompanyDto(
    val id: String,
    val name: String,
    val address: Address
)