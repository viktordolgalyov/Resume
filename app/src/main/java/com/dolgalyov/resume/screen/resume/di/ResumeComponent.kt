package com.dolgalyov.resume.screen.resume.di

import com.dolgalyov.resume.common.di.ScreenScope
import com.dolgalyov.resume.screen.resume.ResumeFragment
import com.dolgalyov.resume.screen.resume.ResumeInputParams
import dagger.BindsInstance
import dagger.Subcomponent

@ScreenScope
@Subcomponent(modules = [ResumeFeatureModule::class])
interface ResumeComponent {

    fun inject(target: ResumeFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance input: ResumeInputParams): ResumeComponent
    }

    interface ComponentProvider {
        fun provideResumeComponent(input: ResumeInputParams): ResumeComponent
    }
}