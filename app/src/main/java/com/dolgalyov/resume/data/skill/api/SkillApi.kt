package com.dolgalyov.resume.data.skill.api

import io.reactivex.Single
import retrofit2.http.GET

interface SkillApi {

    @GET("skills.json")
    fun getSkills(): Single<List<RawSkill>>
}