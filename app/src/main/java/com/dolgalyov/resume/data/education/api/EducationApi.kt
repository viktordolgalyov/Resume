package com.dolgalyov.resume.data.education.api

import io.reactivex.Single
import retrofit2.http.GET

interface EducationApi {

    @GET("education_record.json")
    fun getEducationRecords(): Single<List<RawEducationRecord>>
}