package com.dolgalyov.resume.data.university.api

import retrofit2.http.GET

interface UniversityApi {

    @GET("university.json")
    suspend fun getUniversities(): List<RawUniversity>
}