package com.dolgalyov.resume.data.user.api

import io.reactivex.Single
import retrofit2.http.GET

interface UserApi {

    @GET("user.json")
    fun getUsers(): Single<List<RawUser>>
}