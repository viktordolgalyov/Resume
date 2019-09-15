package com.dolgalyov.resume.data.user

import com.dolgalyov.resume.data.user.api.UserApi
import com.dolgalyov.resume.data.user.source.UserLocalSource
import com.dolgalyov.resume.data.user.source.UserRemoteSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class UserDataModule {

    @Provides
    @Singleton
    fun userRepository(
        localSource: UserLocalSource,
        remoteSource: UserRemoteSource
    ): UserRepository = UserLocalRestRepository(
        localSource = localSource,
        remoteSource = remoteSource
    )

    @Provides
    @Singleton
    fun userLocalSource() = UserLocalSource()

    @Provides
    @Singleton
    fun userRemoteSource(api: UserApi) = UserRemoteSource(api = api)

    @Provides
    @Singleton
    fun userApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)
}