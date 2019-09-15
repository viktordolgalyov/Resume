package com.dolgalyov.resume.data.university.api

import io.reactivex.Single
import retrofit2.http.GET

interface UniversityApi {

    @GET("university.json")
    fun getUniversities(): Single<List<RawUniversity>>
}