package com.dolgalyov.resume.app.di

import com.dolgalyov.resume.BuildConfig
import com.dolgalyov.resume.app.ResumeApp
import com.dolgalyov.resume.common.log.TimberRemoteTree
import com.dolgalyov.resume.common.rx.RxWorkers
import com.dolgalyov.resume.common.util.ResourceProvider
import com.dolgalyov.resume.data.company.CompanyDataModule
import com.dolgalyov.resume.data.education.EducationDataModule
import com.dolgalyov.resume.data.position.PositionDataModule
import com.dolgalyov.resume.data.skill.SkillDataModule
import com.dolgalyov.resume.data.university.UniversityDataModule
import com.dolgalyov.resume.data.user.UserDataModule
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module(
    includes = [
        CompanyDataModule::class,
        EducationDataModule::class,
        PositionDataModule::class,
        SkillDataModule::class,
        UniversityDataModule::class,
        UserDataModule::class
    ]
)
class AppModule {

    @Provides
    @Singleton
    fun retrofitBuilder(): Retrofit = Retrofit.Builder()
        .client(httpClient())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .baseUrl(BuildConfig.API_URL)
        .build()

    @Provides
    @Singleton
    fun workers() = RxWorkers(
        subscribeWorker = Schedulers.io(),
        observeWorker = AndroidSchedulers.mainThread()
    )

    @Provides
    @Singleton
    fun timberTree(): Timber.Tree {
        return if (BuildConfig.DEBUG) Timber.DebugTree() else TimberRemoteTree()
    }

    @Provides
    @Singleton
    fun resourceProvider(app: ResumeApp): ResourceProvider = app

    private fun httpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
        })
        .build()
}