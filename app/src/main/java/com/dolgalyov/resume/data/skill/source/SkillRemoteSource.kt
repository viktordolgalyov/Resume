package com.dolgalyov.resume.data.skill.source

import com.dolgalyov.resume.common.arch.data.RemoteDataSource
import com.dolgalyov.resume.common.exception.ItemNotFoundException
import com.dolgalyov.resume.data.skill.api.RawSkill
import com.dolgalyov.resume.data.skill.api.SkillApi
import com.dolgalyov.resume.data.skill.model.SkillDto

class SkillRemoteSource(
    private val api: SkillApi
) : RemoteDataSource<String, SkillDto> {

    override suspend fun getById(id: String): SkillDto {
        val skills = api.getSkills()
        val skill = skills.firstOrNull { it.id == id }
        return skill?.run(::convertToDto) ?: throw ItemNotFoundException(id)
    }

    private fun convertToDto(raw: RawSkill) = SkillDto(
        id = raw.id,
        name = raw.name
    )
}