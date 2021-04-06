package com.dolgalyov.resume.screen.container

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dolgalyov.resume.screen.container.router.MainRouter

class MainViewModelFactory(private val router: MainRouter) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(
        router = router
    ) as T
}