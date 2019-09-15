package com.dolgalyov.resume.screen.resume.di

import com.dolgalyov.resume.common.di.ScreenScope
import com.dolgalyov.resume.common.rx.RxWorkers
import com.dolgalyov.resume.common.util.ResourceProvider
import com.dolgalyov.resume.domain.service.EducationService
import com.dolgalyov.resume.domain.service.PositionService
import com.dolgalyov.resume.domain.service.SkillService
import com.dolgalyov.resume.domain.service.UsersService
import com.dolgalyov.resume.screen.resume.ResumeInputParams
import com.dolgalyov.resume.screen.resume.presentation.ResumeViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ResumeFeatureModule {

    @Provides
    @ScreenScope
    fun viewModelFactory(
        usersService: UsersService,
        educationService: EducationService,
        positionService: PositionService,
        skillService: SkillService,
        inputParams: ResumeInputParams,
        resourceProvider: ResourceProvider,
        workers: RxWorkers
    ) = ResumeViewModelFactory(
        educationService = educationService,
        usersService = usersService,
        positionService = positionService,
        skillService = skillService,
        inputParams = inputParams,
        resourceProvider = resourceProvider,
        workers = workers
    )
}