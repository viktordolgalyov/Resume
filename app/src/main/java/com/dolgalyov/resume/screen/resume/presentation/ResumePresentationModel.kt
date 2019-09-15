package com.dolgalyov.resume.screen.resume.presentation

import com.dolgalyov.resume.common.arch.presentation.UIModel
import com.dolgalyov.resume.screen.resume.presentation.model.EducationListItem
import com.dolgalyov.resume.screen.resume.presentation.model.PositionListItem
import com.dolgalyov.resume.screen.resume.presentation.model.SkillListItem

data class ResumePresentationModel(
    val isLoading: Boolean,
    val name: String,
    val photo: String,
    val about: String,
    val email: String,
    val address: String,
    val positions: List<PositionListItem>,
    val education: List<EducationListItem>,
    val skills: List<SkillListItem>
) : UIModel