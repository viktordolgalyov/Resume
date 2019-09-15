package com.dolgalyov.resume.screen.resume.presentation

import com.dolgalyov.resume.common.arch.presentation.UIAction
import com.dolgalyov.resume.common.arch.presentation.UIEvent
import com.dolgalyov.resume.common.arch.presentation.UIStateChange
import com.dolgalyov.resume.domain.model.EducationRecord
import com.dolgalyov.resume.domain.model.Position
import com.dolgalyov.resume.domain.model.Skill
import com.dolgalyov.resume.domain.model.User

sealed class ResumeAction : UIAction {
    object EmailClick : ResumeAction()
}

sealed class ResumeEvent : UIEvent {

}

sealed class ResumeChange : UIStateChange {
    data class UserChanged(val user: User) : ResumeChange()
    data class PositionsChanged(val positions: List<Position>) : ResumeChange()
    data class EducationChanged(val education: List<EducationRecord>) : ResumeChange()
    data class SkillsChanged(val skills: List<Skill>) : ResumeChange()
}