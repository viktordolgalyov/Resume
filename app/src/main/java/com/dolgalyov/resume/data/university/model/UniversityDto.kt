package com.dolgalyov.resume.data.university.model

import com.dolgalyov.resume.data.address.Address

data class UniversityDto(
    val id: String,
    val name: String,
    val address: Address
)