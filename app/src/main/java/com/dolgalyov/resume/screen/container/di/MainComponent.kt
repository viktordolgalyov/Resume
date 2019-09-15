package com.dolgalyov.resume.screen.container.di

import com.dolgalyov.resume.common.di.ActivityScope
import com.dolgalyov.resume.screen.container.MainActivity
import com.dolgalyov.resume.screen.resume.di.ResumeComponent
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        EntityServiceModule::class,
        MainModule::class]
)
interface MainComponent {

    fun inject(target: MainActivity)

    fun plusResume(): ResumeComponent.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance target: MainActivity): MainComponent
    }

    interface ComponentProvider {
        fun provideMainComponent(activity: MainActivity): MainComponent
    }
}