package com.dolgalyov.resume.data.position

import com.dolgalyov.resume.data.position.api.PositionApi
import com.dolgalyov.resume.data.position.source.PositionLocalSource
import com.dolgalyov.resume.data.position.source.PositionRemoteSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class PositionDataModule {

    @Provides
    @Singleton
    fun positionRepository(
        localSource: PositionLocalSource,
        remoteSource: PositionRemoteSource
    ): PositionRepository = PositionLocalRestRepository(
        localSource = localSource,
        remoteSource = remoteSource
    )

    @Provides
    @Singleton
    fun positionLocalSource() = PositionLocalSource()

    @Provides
    @Singleton
    fun positionRemoteSource(api: PositionApi) = PositionRemoteSource(api = api)

    @Provides
    @Singleton
    fun positionApi(retrofit: Retrofit): PositionApi = retrofit.create(PositionApi::class.java)
}