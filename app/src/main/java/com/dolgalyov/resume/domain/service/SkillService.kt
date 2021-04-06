package com.dolgalyov.resume.domain.service

import com.dolgalyov.resume.data.skill.SkillRepository
import com.dolgalyov.resume.data.skill.model.SkillDto
import com.dolgalyov.resume.data.user.UserRepository
import com.dolgalyov.resume.domain.model.Skill

class SkillService(
    private val userRepository: UserRepository,
    private val skillRepository: SkillRepository
) {

    suspend fun getSkill(id: String): Skill {
        return skillRepository.getById(id).run(::convertSkill)
    }

    suspend fun getUserSkills(userId: String): List<Skill> {
        return userRepository.getById(userId).skills
            .map { skillId -> getSkill(skillId) }
    }

    private fun convertSkill(dto: SkillDto) = Skill(
        id = dto.id,
        name = dto.name
    )
}