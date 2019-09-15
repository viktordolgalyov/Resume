package com.dolgalyov.resume.common.arch.navigation

import androidx.fragment.app.FragmentManager

interface NavigationHost<T> {

    fun attachHost(host: T)

    fun detachHost()

    fun isAvailable(): Boolean

    fun doWhenAvailable(block: (FragmentManager, Int) -> Unit)
}