package com.dolgalyov.resume.screen.container

import androidx.lifecycle.ViewModel
import com.dolgalyov.resume.screen.container.router.MainRouter

private const val USER_ID = "dbb71707-6961-4d84-bb81-26edb799b8ef"

class MainViewModel(router: MainRouter) : ViewModel() {

    init {
        router.openResume(USER_ID)
    }
}