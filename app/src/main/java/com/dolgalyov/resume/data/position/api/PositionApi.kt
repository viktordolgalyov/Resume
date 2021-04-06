package com.dolgalyov.resume.data.position.api

import retrofit2.http.GET

interface PositionApi {

    @GET("positions.json")
    suspend fun getPositions(): List<RawPosition>
}