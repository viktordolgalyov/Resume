package com.dolgalyov.resume.screen.resume.presentation

import com.dolgalyov.resume.common.arch.presentation.ReduxViewModel
import com.dolgalyov.resume.common.rx.RxWorkers
import com.dolgalyov.resume.common.rx.plusAssign
import com.dolgalyov.resume.domain.service.EducationService
import com.dolgalyov.resume.domain.service.PositionService
import com.dolgalyov.resume.domain.service.SkillService
import com.dolgalyov.resume.domain.service.UsersService
import com.dolgalyov.resume.screen.resume.ResumeInputParams
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ResumeViewModel(
    private val usersService: UsersService,
    private val educationService: EducationService,
    private val positionService: PositionService,
    private val skillService: SkillService,
    private val inputParams: ResumeInputParams,
    reducer: ResumeReducer,
    stateMapper: ResumeStateModelMapper,
    workers: RxWorkers
) : ReduxViewModel<ResumeAction, ResumeChange, ResumeState, ResumePresentationModel>(
    reducer = reducer,
    workers = workers,
    stateToModelMapper = stateMapper
) {
    private val changesSubject = PublishSubject.create<ResumeChange>()
    override var state = ResumeState.EMPTY
    override val errorHandler = ::onErrorOccurred

    override fun onObserverActive(firstTime: Boolean) {
        super.onObserverActive(firstTime)
        if (firstTime) bindActions()
    }

    override fun provideChangesObservable(): Observable<ResumeChange> {
        val userChange = usersService.getUser(inputParams.userId)
            .map { ResumeChange.UserChanged(it) }
            .toObservable()
        val educationChange = educationService.getUserEducation(inputParams.userId)
            .map { ResumeChange.EducationChanged(it) }
            .toObservable()
        val positionChange = positionService.getUserPositions(inputParams.userId)
            .map { ResumeChange.PositionsChanged(it) }
            .toObservable()
        val skillChange = skillService.getUserSkills(inputParams.userId)
            .map { ResumeChange.SkillsChanged(it) }
            .toObservable()

        return changesSubject
            .mergeWith(userChange)
            .mergeWith(educationChange)
            .mergeWith(positionChange)
            .mergeWith(skillChange)
    }

    private fun bindActions() {
        disposables += actions.subscribe { action ->
            when(action) {
                ResumeAction.EmailClick -> {
                    val email = state.user?.email?.value.orEmpty()
                }
            }
        }
    }

    private fun onErrorOccurred(error: Throwable) = Unit
}