package com.dolgalyov.resume.data.skill

import com.dolgalyov.resume.data.skill.api.SkillApi
import com.dolgalyov.resume.data.skill.source.SkillLocalSource
import com.dolgalyov.resume.data.skill.source.SkillRemoteSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class SkillDataModule {

    @Provides
    @Singleton
    fun skillRepository(
        localSource: SkillLocalSource,
        remoteSource: SkillRemoteSource
    ): SkillRepository = SkillLocalRestRepository(
        localSource = localSource,
        remoteSource = remoteSource
    )

    @Provides
    @Singleton
    fun skillLocalSource() = SkillLocalSource()

    @Provides
    @Singleton
    fun skillRemoteSource(api: SkillApi) = SkillRemoteSource(api = api)

    @Provides
    @Singleton
    fun skillApi(retrofit: Retrofit): SkillApi = retrofit.create(SkillApi::class.java)
}