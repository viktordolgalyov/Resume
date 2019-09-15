package com.dolgalyov.resume.domain.service

import com.dolgalyov.resume.data.education.EducationRepository
import com.dolgalyov.resume.data.education.model.EducationRecordDto
import com.dolgalyov.resume.data.university.UniversityRepository
import com.dolgalyov.resume.data.university.model.UniversityDto
import com.dolgalyov.resume.data.user.UserRepository
import com.dolgalyov.resume.domain.model.EducationRecord
import com.dolgalyov.resume.domain.model.University
import io.reactivex.Observable
import io.reactivex.Single

class EducationService(
    private val userRepository: UserRepository,
    private val universityRepository: UniversityRepository,
    private val educationRepository: EducationRepository
) {

    fun getEducationRecord(id: String): Single<EducationRecord> {
        return educationRepository.getById(id).flatMap { educationDto ->
            universityRepository.getById(educationDto.universityId).map { universityDto ->
                val university = convertUniversity(universityDto)
                convertEducation(university, educationDto)
            }
        }
    }

    fun getUserEducation(userId: String): Single<List<EducationRecord>> {
        return userRepository.getById(userId).flatMap {
            Observable
                .fromIterable(it.education)
                .flatMapSingle { educationId -> getEducationRecord(educationId) }
                .toList()
        }
    }

    private fun convertUniversity(dto: UniversityDto) =
        University(
            id = dto.id,
            name = dto.name,
            address = dto.address
        )

    private fun convertEducation(university: University, dto: EducationRecordDto) =
        EducationRecord(
            id = dto.id,
            university = university,
            degree = dto.degree,
            from = dto.from,
            to = dto.to
        )
}