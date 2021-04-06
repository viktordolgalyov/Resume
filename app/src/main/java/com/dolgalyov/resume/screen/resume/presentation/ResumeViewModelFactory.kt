package com.dolgalyov.resume.screen.resume.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dolgalyov.resume.common.util.Workers
import com.dolgalyov.resume.common.util.ResourceProvider
import com.dolgalyov.resume.domain.service.EducationService
import com.dolgalyov.resume.domain.service.PositionService
import com.dolgalyov.resume.domain.service.SkillService
import com.dolgalyov.resume.domain.service.UsersService
import com.dolgalyov.resume.screen.resume.ResumeInputParams

class ResumeViewModelFactory(
    private val usersService: UsersService,
    private val educationService: EducationService,
    private val positionService: PositionService,
    private val skillService: SkillService,
    private val inputParams: ResumeInputParams,
    private val workers: Workers,
    private val resourceProvider: ResourceProvider
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = ResumeViewModel(
        usersService = usersService,
        educationService = educationService,
        positionService = positionService,
        skillService = skillService,
        inputParams = inputParams,
        reducer = ResumeReducer(),
        stateMapper = ResumeStateModelMapper(
            resourceProvider = resourceProvider
        ),
        workers = workers
    ) as T
}