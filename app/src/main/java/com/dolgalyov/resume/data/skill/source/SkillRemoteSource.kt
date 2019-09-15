package com.dolgalyov.resume.data.skill.source

import com.dolgalyov.resume.common.arch.data.RemoteDataSource
import com.dolgalyov.resume.common.exception.ItemNotFoundException
import com.dolgalyov.resume.data.skill.api.RawSkill
import com.dolgalyov.resume.data.skill.api.SkillApi
import com.dolgalyov.resume.data.skill.model.SkillDto
import io.reactivex.Single

class SkillRemoteSource(private val api: SkillApi) :
    RemoteDataSource<String, SkillDto> {

    override fun getById(id: String): Single<SkillDto> {
        return api.getSkills().map { response ->
            val raw = response.firstOrNull { it.id == id }
            raw?.run { convertToDto(this) } ?: throw ItemNotFoundException(
                id
            )
        }
    }

    private fun convertToDto(raw: RawSkill) = SkillDto(
        id = raw.id,
        name = raw.name
    )
}