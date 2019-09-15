package com.dolgalyov.resume.screen.resume.presentation

import com.dolgalyov.resume.common.arch.presentation.Reducer

class ResumeReducer :
    Reducer<ResumeState, ResumeChange> {

    override fun reduce(state: ResumeState, change: ResumeChange): ResumeState {
        return when (change) {
            is ResumeChange.UserChanged -> state.copy(user = change.user)
            is ResumeChange.PositionsChanged -> state.copy(positions = change.positions)
            is ResumeChange.EducationChanged -> state.copy(education = change.education)
            is ResumeChange.SkillsChanged -> state.copy(skills = change.skills)
            else -> state
        }
    }
}