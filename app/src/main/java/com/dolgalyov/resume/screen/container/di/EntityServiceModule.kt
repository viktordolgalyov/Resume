package com.dolgalyov.resume.screen.container.di

import com.dolgalyov.resume.data.company.CompanyRepository
import com.dolgalyov.resume.data.education.EducationRepository
import com.dolgalyov.resume.data.position.PositionRepository
import com.dolgalyov.resume.data.skill.SkillRepository
import com.dolgalyov.resume.data.university.UniversityRepository
import com.dolgalyov.resume.data.user.UserRepository
import com.dolgalyov.resume.domain.service.EducationService
import com.dolgalyov.resume.domain.service.PositionService
import com.dolgalyov.resume.domain.service.SkillService
import com.dolgalyov.resume.domain.service.UsersService
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class EntityServiceModule {

    @Provides
    @Reusable
    fun educationService(
        userRepository: UserRepository,
        universityRepository: UniversityRepository,
        educationRepository: EducationRepository
    ) = EducationService(
        userRepository = userRepository,
        universityRepository = universityRepository,
        educationRepository = educationRepository
    )

    @Provides
    @Reusable
    fun positionService(
        userRepository: UserRepository,
        companyRepository: CompanyRepository,
        positionRepository: PositionRepository
    ) = PositionService(
        usersRepository = userRepository,
        companyRepository = companyRepository,
        positionRepository = positionRepository
    )

    @Provides
    @Reusable
    fun skillService(
        skillRepository: SkillRepository,
        userRepository: UserRepository
    ) = SkillService(
        userRepository = userRepository,
        skillRepository = skillRepository
    )

    @Provides
    @Reusable
    fun usersService(
        userRepository: UserRepository
    ) = UsersService(
        repository = userRepository
    )
}