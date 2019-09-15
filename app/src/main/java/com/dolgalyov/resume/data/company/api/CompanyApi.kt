package com.dolgalyov.resume.data.company.api

import io.reactivex.Single
import retrofit2.http.GET

interface CompanyApi {

    @GET("companies.json")
    fun getCompanies(): Single<List<RawCompany>>
}