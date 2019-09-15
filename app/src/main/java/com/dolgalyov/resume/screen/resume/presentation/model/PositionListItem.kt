package com.dolgalyov.resume.screen.resume.presentation.model

data class PositionListItem(
    val id: String,
    val title: String,
    val company: String,
    val description: String,
    val address: String,
    val fromTo: String
)