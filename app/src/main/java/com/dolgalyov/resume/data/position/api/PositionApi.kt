package com.dolgalyov.resume.data.position.api

import io.reactivex.Single
import retrofit2.http.GET

interface PositionApi {

    @GET("positions.json")
    fun getPositions(): Single<List<RawPosition>>
}