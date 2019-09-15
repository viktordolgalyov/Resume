package com.dolgalyov.resume.data.user.api

import com.dolgalyov.resume.data.address.RawAddress

data class RawUser(
    val id: String,
    val name: String,
    val photo: String,
    val about: String,
    val email: String,
    val address: RawAddress,
    val positions: List<String>,
    val education: List<String>,
    val skills: List<String>
)