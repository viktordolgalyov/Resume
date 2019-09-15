package com.dolgalyov.resume.data.university.api

import com.dolgalyov.resume.data.address.RawAddress

data class RawUniversity(
    val id: String,
    val name: String,
    val address: RawAddress
)