package com.dolgalyov.resume.domain.model

import com.dolgalyov.resume.data.address.Address

data class User(
    val id: String,
    val name: String,
    val photo: String,
    val about: String,
    val email: Email,
    val address: Address
)