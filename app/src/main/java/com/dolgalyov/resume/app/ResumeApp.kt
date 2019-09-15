package com.dolgalyov.resume.app

import android.app.Application
import com.dolgalyov.resume.app.di.DaggerAppComponent
import com.dolgalyov.resume.common.util.ResourceProvider
import com.dolgalyov.resume.screen.container.MainActivity
import com.dolgalyov.resume.screen.container.di.MainComponent
import timber.log.Timber
import javax.inject.Inject

class ResumeApp : Application(), MainComponent.ComponentProvider, ResourceProvider {
    private val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
    @Inject lateinit var logTree: Timber.Tree

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        Timber.plant(logTree)
    }

    override fun provideMainComponent(activity: MainActivity): MainComponent =
        component.plusMain().create(activity)
}