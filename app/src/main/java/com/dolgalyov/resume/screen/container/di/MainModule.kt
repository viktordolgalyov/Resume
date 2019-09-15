package com.dolgalyov.resume.screen.container.di

import androidx.fragment.app.FragmentActivity
import com.dolgalyov.resume.R
import com.dolgalyov.resume.common.arch.navigation.ActivityNavigationHost
import com.dolgalyov.resume.common.arch.navigation.NavigationHost
import com.dolgalyov.resume.common.di.ActivityScope
import com.dolgalyov.resume.screen.container.MainViewModelFactory
import com.dolgalyov.resume.screen.container.navigation.Navigator
import com.dolgalyov.resume.screen.container.router.MainActivityRouter
import com.dolgalyov.resume.screen.container.router.MainRouter
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    @ActivityScope
    fun navigator(host: NavigationHost<FragmentActivity>) = Navigator(
        host = host
    )

    @Provides
    @ActivityScope
    fun navigationHost(): NavigationHost<FragmentActivity> =
        ActivityNavigationHost(R.id.fragmentContainer)

    @Provides
    @ActivityScope
    fun router(navigator: Navigator): MainRouter = MainActivityRouter(
        navigator = navigator
    )

    @Provides
    @ActivityScope
    fun mainViewModelFactory(router: MainRouter) = MainViewModelFactory(
        router = router
    )
}