package com.dolgalyov.resume.data.user.api

import retrofit2.http.GET

interface UserApi {

    @GET("user.json")
    suspend fun getUsers(): List<RawUser>
}