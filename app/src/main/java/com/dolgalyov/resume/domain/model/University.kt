package com.dolgalyov.resume.domain.model

import com.dolgalyov.resume.data.address.Address

data class University(
    val id: String,
    val name: String,
    val address: Address
)