package com.dolgalyov.resume.data.skill.api

import retrofit2.http.GET

interface SkillApi {

    @GET("skills.json")
    suspend fun getSkills(): List<RawSkill>
}