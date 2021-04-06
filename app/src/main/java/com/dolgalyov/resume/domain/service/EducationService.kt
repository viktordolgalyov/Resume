package com.dolgalyov.resume.domain.service

import com.dolgalyov.resume.data.education.EducationRepository
import com.dolgalyov.resume.data.education.model.EducationRecordDto
import com.dolgalyov.resume.data.university.UniversityRepository
import com.dolgalyov.resume.data.university.model.UniversityDto
import com.dolgalyov.resume.data.user.UserRepository
import com.dolgalyov.resume.domain.model.EducationRecord
import com.dolgalyov.resume.domain.model.University

class EducationService(
    private val userRepository: UserRepository,
    private val universityRepository: UniversityRepository,
    private val educationRepository: EducationRepository
) {

    suspend fun getEducationRecord(id: String): EducationRecord {
        val record = educationRepository.getById(id)
        val university = universityRepository.getById(record.universityId).run(::convertUniversity)
        return convertEducation(university, record)
    }

    suspend fun getUserEducation(userId: String): List<EducationRecord> {
        return userRepository.getById(userId).education
            .map { educationId -> getEducationRecord(educationId) }
    }

    private fun convertUniversity(dto: UniversityDto) = University(
        id = dto.id,
        name = dto.name,
        address = dto.address
    )

    private fun convertEducation(
        university: University,
        dto: EducationRecordDto
    ) = EducationRecord(
        id = dto.id,
        university = university,
        degree = dto.degree,
        from = dto.from,
        to = dto.to
    )
}