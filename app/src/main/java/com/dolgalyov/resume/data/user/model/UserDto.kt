package com.dolgalyov.resume.data.user.model

import com.dolgalyov.resume.data.address.Address

data class UserDto(
    val id: String,
    val name: String,
    val photo: String,
    val about: String,
    val email: String,
    val address: Address,
    val positions: List<String>,
    val education: List<String>,
    val skills: List<String>
)