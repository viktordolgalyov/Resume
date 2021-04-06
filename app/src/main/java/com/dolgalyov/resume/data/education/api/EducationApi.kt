package com.dolgalyov.resume.data.education.api

import retrofit2.http.GET

interface EducationApi {

    @GET("education_record.json")
    suspend fun getEducationRecords(): List<RawEducationRecord>
}