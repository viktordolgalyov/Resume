package com.dolgalyov.resume.screen.resume.presentation

import androidx.lifecycle.viewModelScope
import com.dolgalyov.resume.common.arch.presentation.ReduxViewModel
import com.dolgalyov.resume.common.util.Workers
import com.dolgalyov.resume.domain.service.EducationService
import com.dolgalyov.resume.domain.service.PositionService
import com.dolgalyov.resume.domain.service.SkillService
import com.dolgalyov.resume.domain.service.UsersService
import com.dolgalyov.resume.screen.resume.ResumeInputParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

class ResumeViewModel(
    private val usersService: UsersService,
    private val educationService: EducationService,
    private val positionService: PositionService,
    private val skillService: SkillService,
    private val inputParams: ResumeInputParams,
    reducer: ResumeReducer,
    stateMapper: ResumeStateModelMapper,
    workers: Workers
) : ReduxViewModel<ResumeAction, ResumeChange, ResumeState, ResumePresentationModel>(
    reducer = reducer,
    workers = workers,
    stateToModelMapper = stateMapper
) {
    override var state = ResumeState.EMPTY
    override val errorHandler = ::onErrorOccurred

    override fun onObserverActive(firstTime: Boolean) {
        super.onObserverActive(firstTime)
        if (firstTime) {
            loadUser()
            loadEducation()
            loadPositions()
            loadSkills()
        }
    }

    override suspend fun provideChangesObservable(): Flow<ResumeChange> = emptyFlow()

    override fun processAction(action: ResumeAction) {
        when (action) {
            ResumeAction.EmailClick -> {
                val email = state.user?.email?.value.orEmpty()
            }
        }
    }

    private fun onErrorOccurred(error: Throwable) = Unit

    private fun loadUser() {
        viewModelScope.launch(workers.subscribeWorker) {
            execute(
                action = { usersService.getUser(inputParams.userId) },
                onSuccess = { sendChange(ResumeChange.UserChanged(it)) }
            )
        }
    }

    private fun loadEducation() {
        viewModelScope.launch(workers.subscribeWorker) {
            execute(
                action = { educationService.getUserEducation(inputParams.userId) },
                onSuccess = { sendChange(ResumeChange.EducationChanged(it)) }
            )
        }
    }

    private fun loadPositions() {
        viewModelScope.launch(workers.subscribeWorker) {
            execute(
                action = { positionService.getUserPositions(inputParams.userId) },
                onSuccess = { sendChange(ResumeChange.PositionsChanged(it)) }
            )
        }
    }

    private fun loadSkills() {
        viewModelScope.launch(workers.subscribeWorker) {
            execute(
                action = { skillService.getUserSkills(inputParams.userId) },
                onSuccess = { sendChange(ResumeChange.SkillsChanged(it)) }
            )
        }
    }
}