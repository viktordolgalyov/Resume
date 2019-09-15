package com.dolgalyov.resume.data.university

import com.dolgalyov.resume.data.university.api.UniversityApi
import com.dolgalyov.resume.data.university.source.UniversityLocalSource
import com.dolgalyov.resume.data.university.source.UniversityRemoteSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class UniversityDataModule {

    @Provides
    @Singleton
    fun universityRepository(
        localSource: UniversityLocalSource,
        remoteSource: UniversityRemoteSource
    ): UniversityRepository = UniversityLocalRestRepository(
        localSource = localSource,
        remoteSource = remoteSource
    )

    @Provides
    @Singleton
    fun universityLocalSource() = UniversityLocalSource()

    @Provides
    @Singleton
    fun universityRemoteSource(api: UniversityApi) = UniversityRemoteSource(api = api)

    @Provides
    @Singleton
    fun universityApi(retrofit: Retrofit): UniversityApi =
        retrofit.create(UniversityApi::class.java)
}