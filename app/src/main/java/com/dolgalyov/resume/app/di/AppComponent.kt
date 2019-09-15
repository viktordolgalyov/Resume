package com.dolgalyov.resume.app.di

import com.dolgalyov.resume.app.ResumeApp
import com.dolgalyov.resume.screen.container.di.MainComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(target: ResumeApp)

    fun plusMain(): MainComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance target: ResumeApp): AppComponent
    }
}