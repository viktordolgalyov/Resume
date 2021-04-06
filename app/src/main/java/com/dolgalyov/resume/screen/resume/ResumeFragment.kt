package com.dolgalyov.resume.screen.resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dolgalyov.resume.BuildConfig
import com.dolgalyov.resume.R
import com.dolgalyov.resume.app.GlideApp
import com.dolgalyov.resume.common.arch.presentation.UIEvent
import com.dolgalyov.resume.screen.resume.di.ResumeComponent
import com.dolgalyov.resume.screen.resume.presentation.ResumePresentationModel
import com.dolgalyov.resume.screen.resume.presentation.ResumeViewModel
import com.dolgalyov.resume.screen.resume.presentation.ResumeViewModelFactory
import com.dolgalyov.resume.screen.resume.view.EducationAdapter
import com.dolgalyov.resume.screen.resume.view.PositionAdapter
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_resume.*
import kotlinx.android.synthetic.main.item_chip.view.*
import javax.inject.Inject

private const val EXTRA_USER_ID = "${BuildConfig.APPLICATION_ID}.EXTRA_USER_ID"

class ResumeFragment : Fragment(R.layout.fragment_resume) {

    companion object {

        fun create(userId: String): ResumeFragment {
            val args = Bundle()
                .apply { putString(EXTRA_USER_ID, userId) }
            return ResumeFragment()
                .apply { arguments = args }
        }
    }

    private val component by lazy {
        val userId = arguments?.getString(EXTRA_USER_ID).orEmpty()
        (activity as ResumeComponent.ComponentProvider).provideResumeComponent(
            input = ResumeInputParams(userId)
        )
    }
    private val viewModel by viewModels<ResumeViewModel> { viewModelFactory }

    @Inject lateinit var viewModelFactory: ResumeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.observableModel.observe(viewLifecycleOwner, Observer(::renderModel))
        viewModel.observableEvent.observe(viewLifecycleOwner, Observer(::renderEvent))
    }

    private fun renderModel(model: ResumePresentationModel) {
        if (photo.drawable == null) {
            GlideApp.with(photo).clear(photo)
            GlideApp.with(photo)
                .load(model.photo)
                .circleCrop()
                .into(photo)
        }
        progress.isVisible = model.isLoading

        name.text = model.name
        about.isVisible = model.about.isNotBlank()
        about.text = model.about
        email.isVisible = model.email.isNotBlank()
        email.text = model.email
        address.isVisible = model.address.isNotBlank()
        address.text = model.address
        (positions.adapter as PositionAdapter).submitList(model.positions)
        (education.adapter as EducationAdapter).submitList(model.education)

        model.skills
            .map { it.title }
            .filterNot { containsSkill(it) }
            .map { createSkill(it) }
            .forEach { skills.addView(it) }
    }

    private fun renderEvent(event: UIEvent) = Unit

    private fun initViews() {
        with(positions) {
            layoutManager = LinearLayoutManager(context)
                .apply { orientation = RecyclerView.VERTICAL }
            adapter = PositionAdapter()
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }

        with(education) {
            layoutManager = LinearLayoutManager(context)
                .apply { orientation = RecyclerView.VERTICAL }
            adapter = EducationAdapter()
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun createSkill(text: String): Chip {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chip, skills, false)
        return view.chip.apply { this.text = text }
    }

    private fun containsSkill(text: String): Boolean {
        return (0..skills.childCount)
            .map { skills.getChildAt(it) }
            .filterIsInstance(Chip::class.java)
            .map { it.text }
            .any { it == text }
    }
}