package com.dolgalyov.resume.common.arch.navigation

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class ActivityNavigationHost(private val containerId: Int) : NavigationHost<FragmentActivity> {
    private var host: FragmentActivity? = null
    private var pendingAction: ((FragmentManager, Int) -> Unit)? = null

    override fun attachHost(host: FragmentActivity) {
        this.host = host
        if (isAvailable()) {
            executePendingAction()
        } else {
            waitForLifecycle { executePendingAction() }
        }
    }

    override fun detachHost() {
        this.host = null
    }

    override fun isAvailable(): Boolean {
        return host?.run { lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) } == true
    }

    override fun doWhenAvailable(block: (FragmentManager, Int) -> Unit) {
        if (isAvailable()) {
            pendingAction = block
            executePendingAction()
        } else {
            pendingAction = block
            waitForLifecycle { executePendingAction() }
        }
    }

    private fun executePendingAction() {
        host?.run {
            pendingAction?.invoke(supportFragmentManager, containerId)
            pendingAction = null
        }
    }

    private fun waitForLifecycle(action: () -> Unit) {
        host?.lifecycle?.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onActivityStarted() {
                action()
                host?.lifecycle?.removeObserver(this)
            }
        })
    }
}