package com.dolgalyov.resume.domain.service

import com.dolgalyov.resume.common.rx.toSingle
import com.dolgalyov.resume.data.address.Address
import com.dolgalyov.resume.data.education.EducationRepository
import com.dolgalyov.resume.data.education.model.EducationRecordDto
import com.dolgalyov.resume.data.university.UniversityRepository
import com.dolgalyov.resume.data.university.model.UniversityDto
import com.dolgalyov.resume.data.user.UserRepository
import com.dolgalyov.resume.data.user.model.UserDto
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import java.util.*

class EducationServiceTest {
    @Mock lateinit var universityRepository: UniversityRepository
    @Mock lateinit var userRepository: UserRepository
    @Mock lateinit var educationRepository: EducationRepository
    lateinit var educationService: EducationService

    @Before
    fun prepare() {
        initMocks(this)
        educationService = EducationService(
            userRepository = userRepository,
            universityRepository = universityRepository,
            educationRepository = educationRepository
        )
    }

    @Test
    fun `should return education record correctly`() {
        val expectedRecord = EducationRecordDto(
            id = UUID.randomUUID().toString(),
            universityId = UUID.randomUUID().toString(),
            from = Date(),
            to = Date(),
            degree = "Master"
        )
        val expectedUniversity = UniversityDto(
            id = expectedRecord.universityId,
            name = "University ${expectedRecord.universityId}",
            address = Address(
                country = "Country",
                city = "City"
            )
        )
        whenever(universityRepository.getById(any())).thenReturn(expectedUniversity.toSingle())
        whenever(educationRepository.getById(any())).thenReturn(expectedRecord.toSingle())

        val actual = educationService.getEducationRecord(expectedRecord.id).blockingGet()

        verify(universityRepository).getById(expectedUniversity.id)
        verify(educationRepository).getById(expectedRecord.id)

        assertEquals(expectedRecord.id, actual.id)
        assertEquals(expectedRecord.universityId, actual.university.id)
        assertEquals(expectedRecord.from, actual.from)
        assertEquals(expectedRecord.to, actual.to)
        assertEquals(expectedRecord.degree, actual.degree)
        assertEquals(expectedUniversity.name, actual.university.name)
        assertEquals(expectedUniversity.address.city, actual.university.address.city)
        assertEquals(expectedUniversity.address.country, actual.university.address.country)
    }

    @Test
    fun `should return education records for the user correctly`() {
        val educationIds = (0..10).map { UUID.randomUUID().toString() }
        val userId = UUID.randomUUID().toString()
        val user = mock<UserDto>().apply {
            whenever(id).thenReturn(userId)
            whenever(education).thenReturn(educationIds)
        }

        whenever(userRepository.getById(any())).thenReturn(user.toSingle())
        whenever(educationRepository.getById(any())).thenAnswer {
            val argumentId = it.arguments.first() as String
            EducationRecordDto(
                id = argumentId,
                degree = "Master",
                universityId = UUID.randomUUID().toString(),
                from = Date(),
                to = Date()
            ).toSingle()
        }
        whenever(universityRepository.getById(any())).thenAnswer {
            val argumentId = it.arguments.first() as String
            UniversityDto(
                id = argumentId,
                name = "University $argumentId",
                address = Address(
                    country = "Country",
                    city = "City"
                )
            ).toSingle()
        }

        val actual = educationService.getUserEducation(userId).blockingGet()

        verify(userRepository).getById(userId)
        educationIds.forEach { verify(educationRepository).getById(it) }
        assertEquals(educationIds.sorted(), actual.map { it.id }.sorted())
    }
}