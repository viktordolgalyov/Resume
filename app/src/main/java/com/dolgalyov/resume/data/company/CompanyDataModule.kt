package com.dolgalyov.resume.data.company

import com.dolgalyov.resume.data.company.api.CompanyApi
import com.dolgalyov.resume.data.company.source.CompanyLocalSource
import com.dolgalyov.resume.data.company.source.CompanyRemoteSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class CompanyDataModule {

    @Provides
    @Singleton
    fun companyRepository(
        localSource: CompanyLocalSource,
        remoteSource: CompanyRemoteSource
    ): CompanyRepository = CompanyLocalRestRepository(
        localSource = localSource,
        remoteSource = remoteSource
    )

    @Provides
    @Singleton
    fun companyLocalSource() = CompanyLocalSource()

    @Provides
    @Singleton
    fun companyRemoteSource(api: CompanyApi) = CompanyRemoteSource(api = api)

    @Provides
    @Singleton
    fun companyApi(retrofit: Retrofit): CompanyApi = retrofit.create(CompanyApi::class.java)
}