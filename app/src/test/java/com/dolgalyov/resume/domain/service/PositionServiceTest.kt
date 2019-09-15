package com.dolgalyov.resume.domain.service

import com.dolgalyov.resume.common.rx.toSingle
import com.dolgalyov.resume.data.address.Address
import com.dolgalyov.resume.data.company.CompanyRepository
import com.dolgalyov.resume.data.company.model.CompanyDto
import com.dolgalyov.resume.data.position.PositionRepository
import com.dolgalyov.resume.data.position.model.PositionDto
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

class PositionServiceTest {
    @Mock lateinit var userRepository: UserRepository
    @Mock lateinit var companyRepository: CompanyRepository
    @Mock lateinit var positionRepository: PositionRepository
    lateinit var positionService: PositionService

    @Before
    fun prepare() {
        initMocks(this)
        positionService = PositionService(
            usersRepository = userRepository,
            companyRepository = companyRepository,
            positionRepository = positionRepository
        )
    }

    @Test
    fun `should return position correctly`() {
        val expected = PositionDto(
            id = UUID.randomUUID().toString(),
            name = "Position",
            companyId = UUID.randomUUID().toString(),
            description = "description",
            from = Date(),
            to = null
        )

        val expectedCompany = CompanyDto(
            id = expected.companyId,
            name = "Company ${expected.companyId}",
            address = Address(
                country = "Country",
                city = "City"
            )
        )

        whenever(positionRepository.getById(any())).thenReturn(expected.toSingle())
        whenever(companyRepository.getById(any())).thenReturn(expectedCompany.toSingle())

        val actual = positionService.getPosition(expected.id).blockingGet()

        verify(positionRepository).getById(expected.id)
        verify(companyRepository).getById(expected.companyId)

        assertEquals(expected.id, actual.id)
        assertEquals(expected.companyId, actual.company.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.description, actual.description)
        assertEquals(expected.from, actual.from)
        assertEquals(expected.to, actual.to)
        assertEquals(expectedCompany.name, actual.company.name)
        assertEquals(expectedCompany.address.city, actual.company.address.city)
        assertEquals(expectedCompany.address.country, actual.company.address.country)
    }

    @Test
    fun `should return user positions correctly`() {
        val positionIds = (0..10).map { UUID.randomUUID().toString() }
        val userId = UUID.randomUUID().toString()
        val user = mock<UserDto>().apply {
            whenever(id).thenReturn(userId)
            whenever(positions).thenReturn(positionIds)
        }

        whenever(userRepository.getById(any())).thenReturn(user.toSingle())
        whenever(positionRepository.getById(any())).thenAnswer {
            val argumentId = it.arguments.first() as String
            PositionDto(
                id = argumentId,
                name = "Position",
                companyId = UUID.randomUUID().toString(),
                description = "description",
                from = Date(),
                to = null
            ).toSingle()
        }
        whenever(companyRepository.getById(any())).thenAnswer {
            val argumentId = it.arguments.first() as String
            CompanyDto(
                id = argumentId,
                name = "Company $argumentId",
                address = Address(
                    country = "Country",
                    city = "City"
                )
            ).toSingle()
        }

        val actual = positionService.getUserPositions(userId).blockingGet()

        verify(userRepository).getById(userId)
        positionIds.forEach { verify(positionRepository).getById(it) }
        assertEquals(positionIds.sorted(), actual.map { it.id }.sorted())
    }
}