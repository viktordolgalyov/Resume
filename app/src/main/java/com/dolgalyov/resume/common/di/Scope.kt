package com.dolgalyov.resume.common.di

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FlowScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenScope