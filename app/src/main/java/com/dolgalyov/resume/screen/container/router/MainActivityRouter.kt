package com.dolgalyov.resume.screen.container.router

import com.dolgalyov.resume.screen.container.navigation.Navigator
import com.dolgalyov.resume.screen.container.navigation.Screen

class MainActivityRouter(private val navigator: Navigator) : MainRouter {

    override fun openResume(userId: String) = navigator.setScreen(Screen.Resume(userId))
}