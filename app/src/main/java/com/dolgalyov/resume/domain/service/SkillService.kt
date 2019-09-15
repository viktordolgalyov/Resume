package com.dolgalyov.resume.domain.service

import com.dolgalyov.resume.data.skill.SkillRepository
import com.dolgalyov.resume.data.skill.model.SkillDto
import com.dolgalyov.resume.data.user.UserRepository
import com.dolgalyov.resume.domain.model.Skill
import io.reactivex.Observable
import io.reactivex.Single

class SkillService(
    private val userRepository: UserRepository,
    private val skillRepository: SkillRepository
) {

    fun getSkill(id: String): Single<Skill> {
        return skillRepository.getById(id)
            .map { convertSkill(it) }
    }

    fun getUserSkills(userId: String): Single<List<Skill>> {
        return userRepository.getById(userId).flatMap {
            Observable.fromIterable(it.skills)
                .flatMapSingle { skillId -> getSkill(skillId) }
                .toList()
        }
    }

    private fun convertSkill(dto: SkillDto) = Skill(
        id = dto.id,
        name = dto.name
    )
}