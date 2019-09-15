package com.dolgalyov.resume.screen.resume.presentation

import com.dolgalyov.resume.common.arch.presentation.UIState
import com.dolgalyov.resume.domain.model.EducationRecord
import com.dolgalyov.resume.domain.model.Position
import com.dolgalyov.resume.domain.model.Skill
import com.dolgalyov.resume.domain.model.User

data class ResumeState(
    val user: User?,
    val skills: List<Skill>,
    val positions: List<Position>,
    val education: List<EducationRecord>
) : UIState {

    companion object {
        val EMPTY = ResumeState(
            user = null,
            skills = emptyList(),
            positions = emptyList(),
            education = emptyList()
        )
    }
}