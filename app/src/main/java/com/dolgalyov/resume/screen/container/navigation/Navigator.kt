package com.dolgalyov.resume.screen.container.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.dolgalyov.resume.common.arch.navigation.NavigationHost

class Navigator(private val host: NavigationHost<*>) {

    fun setScreen(screen: Screen) {
        host.doWhenAvailable { fragmentManager: FragmentManager, containerId: Int ->
            val fragment = screen.createFragment()
            setFragment(fragmentManager, containerId, fragment)
        }
    }

    private fun setFragment(fm: FragmentManager, containerId: Int, fragment: Fragment) {
        fm.beginTransaction()
            .replace(containerId, fragment, fragment::class.java.name)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}