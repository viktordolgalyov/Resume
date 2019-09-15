package com.dolgalyov.resume.screen.container.navigation

import androidx.fragment.app.Fragment
import com.dolgalyov.resume.screen.resume.ResumeFragment

sealed class Screen {

    abstract fun createFragment(): Fragment

    class Resume(val userId: String) : Screen() {
        override fun createFragment() = ResumeFragment.create(userId = userId)
    }
}