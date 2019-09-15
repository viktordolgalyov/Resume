package com.dolgalyov.resume.domain.service

import com.dolgalyov.resume.common.rx.toSingle
import com.dolgalyov.resume.data.skill.SkillRepository
import com.dolgalyov.resume.data.skill.model.SkillDto
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

class SkillServiceTest {
    @Mock lateinit var skillRepository: SkillRepository
    @Mock lateinit var userRepository: UserRepository
    lateinit var skillService: SkillService

    @Before
    fun prepare() {
        initMocks(this)
        skillService = SkillService(
            userRepository = userRepository,
            skillRepository = skillRepository
        )
    }

    @Test
    fun `should return skill correctly`() {
        val expected = SkillDto(
            id = UUID.randomUUID().toString(),
            name = "Kotlin"
        )
        whenever(skillRepository.getById(any())).thenReturn(expected.toSingle())

        val actual = skillService.getSkill(expected.id).blockingGet()

        verify(skillRepository).getById(expected.id)

        assertEquals(expected.id, actual.id)
        assertEquals(expected.name, actual.name)
    }

    @Test
    fun `should return user skills correctly`() {
        val skillIds = (0..10).map { UUID.randomUUID().toString() }
        val userId = UUID.randomUUID().toString()
        val user = mock<UserDto>().apply {
            whenever(id).thenReturn(userId)
            whenever(skills).thenReturn(skillIds)
        }

        whenever(userRepository.getById(any())).thenReturn(user.toSingle())
        whenever(skillRepository.getById(any())).thenAnswer {
            val argumentId = it.arguments.first() as String
            mock<SkillDto>().apply {
                whenever(id).thenReturn(argumentId)
                whenever(name).thenReturn("Skill: $argumentId")
            }.toSingle()
        }

        val actual = skillService.getUserSkills(userId).blockingGet()

        verify(userRepository).getById(userId)
        skillIds.forEach { verify(skillRepository).getById(it) }

        assertEquals(skillIds.sorted(), actual.map { it.id }.sorted())
    }
}