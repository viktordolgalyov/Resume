package com.dolgalyov.resume.data.company.api

import retrofit2.http.GET

interface CompanyApi {

    @GET("companies.json")
    suspend fun getCompanies(): List<RawCompany>
}