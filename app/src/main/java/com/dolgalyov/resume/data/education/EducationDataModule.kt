package com.dolgalyov.resume.data.education

import com.dolgalyov.resume.data.education.api.EducationApi
import com.dolgalyov.resume.data.education.source.EducationLocalSource
import com.dolgalyov.resume.data.education.source.EducationRemoteSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class EducationDataModule {

    @Provides
    @Singleton
    fun educationRepository(
        localSource: EducationLocalSource,
        remoteSource: EducationRemoteSource
    ): EducationRepository = EducationLocalRestRepository(
        localSource = localSource,
        remoteSource = remoteSource
    )

    @Provides
    @Singleton
    fun educationLocalSource() = EducationLocalSource()

    @Provides
    @Singleton
    fun educationRemoteSource(api: EducationApi) = EducationRemoteSource(api = api)

    @Provides
    @Singleton
    fun educationApi(retrofit: Retrofit): EducationApi = retrofit.create(EducationApi::class.java)
}