package com.dolgalyov.resume.screen.container

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.dolgalyov.resume.R
import com.dolgalyov.resume.common.android.setWindowTransparency
import com.dolgalyov.resume.common.android.updateMargin
import com.dolgalyov.resume.common.arch.navigation.NavigationHost
import com.dolgalyov.resume.screen.container.di.MainComponent
import com.dolgalyov.resume.screen.resume.ResumeInputParams
import com.dolgalyov.resume.screen.resume.di.ResumeComponent
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), ResumeComponent.ComponentProvider {
    private val component by lazy {
        (application as MainComponent.ComponentProvider).provideMainComponent(this)
    }

    @Inject lateinit var navigationHost: NavigationHost<FragmentActivity>
    @Inject lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setWindowTransparent()
        ViewModelProvider(this, viewModelFactory).get<MainViewModel>()
    }

    override fun onStart() {
        super.onStart()
        navigationHost.attachHost(this)
    }

    override fun onStop() {
        super.onStop()
        navigationHost.detachHost()
    }

    override fun provideResumeComponent(input: ResumeInputParams): ResumeComponent {
        return component.plusResume().create(input)
    }

    private fun setWindowTransparent() {
        setWindowTransparency { statusBarSize, _ ->
            fragmentContainer.updateMargin(top = statusBarSize)
        }
    }
}