package com.dolgalyov.resume.domain.service

import com.dolgalyov.resume.common.rx.toSingle
import com.dolgalyov.resume.data.address.Address
import com.dolgalyov.resume.data.user.UserRepository
import com.dolgalyov.resume.data.user.model.UserDto
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import java.util.*

class UsersServiceTest {
    @Mock lateinit var userRepository: UserRepository
    lateinit var usersService: UsersService

    @Before
    fun prepare() {
        initMocks(this)
        usersService = UsersService(
            repository = userRepository
        )
    }

    @Test
    fun `should return user correctly`() {
        val id = UUID.randomUUID().toString()
        val expected = UserDto(
            id = id,
            name = "John Doe",
            photo = "photo",
            about = "about",
            email = "test@test.com",
            address = Address(
                country = "Country",
                city = "City"
            ),
            positions = (0..5).map { UUID.randomUUID().toString() },
            education = listOf(UUID.randomUUID().toString()),
            skills = (0..5).map { UUID.randomUUID().toString() }
        )

        whenever(userRepository.getById(any())).thenReturn(expected.toSingle())

        val actual = usersService.getUser(id).blockingGet()

        verify(userRepository).getById(id)

        assertEquals(expected.id, actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.photo, actual.photo)
        assertEquals(expected.about, actual.about)
        assertEquals(expected.email, actual.email.value)
        assertEquals(expected.address.city, actual.address.city)
        assertEquals(expected.address.country, actual.address.country)
    }
}