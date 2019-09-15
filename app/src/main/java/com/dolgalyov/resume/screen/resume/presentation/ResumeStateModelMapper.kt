package com.dolgalyov.resume.screen.resume.presentation

import com.dolgalyov.resume.R
import com.dolgalyov.resume.common.arch.presentation.StateToModelMapper
import com.dolgalyov.resume.common.util.ResourceProvider
import com.dolgalyov.resume.common.util.formatShortDate
import com.dolgalyov.resume.data.address.Address
import com.dolgalyov.resume.domain.model.EducationRecord
import com.dolgalyov.resume.domain.model.Position
import com.dolgalyov.resume.domain.model.Skill
import com.dolgalyov.resume.screen.resume.presentation.model.EducationListItem
import com.dolgalyov.resume.screen.resume.presentation.model.PositionListItem
import com.dolgalyov.resume.screen.resume.presentation.model.SkillListItem
import java.util.*

class ResumeStateModelMapper(private val resourceProvider: ResourceProvider) :
    StateToModelMapper<ResumeState, ResumePresentationModel> {

    override fun mapStateToModel(state: ResumeState): ResumePresentationModel {
        val isLoading = state.user == null

        val address = createAddressText(state.user?.address)
        val positions = state.positions
            .sortedByDescending { it.from.time }
            .map { it.toListItem() }
        val educations = state.education
            .sortedByDescending { it.from.time }
            .map { it.toListItem() }
        val skills = state.skills
            .map { it.toListItem() }

        return ResumePresentationModel(
            isLoading = isLoading,
            name = state.user?.name.orEmpty(),
            photo = state.user?.photo.orEmpty(),
            about = state.user?.about.orEmpty(),
            email = state.user?.email?.value.orEmpty(),
            address = address,
            positions = positions,
            education = educations,
            skills = skills
        )
    }

    private fun Position.toListItem() = PositionListItem(
        id = this.id,
        title = this.name,
        company = this.company.name,
        description = this.description,
        address = createAddressText(this.company.address),
        fromTo = createFromToText(this.from, this.to)
    )

    private fun EducationRecord.toListItem() = EducationListItem(
        id = this.id,
        university = this.university.name,
        address = createAddressText(this.university.address),
        degree = this.degree,
        fromTo = createFromToText(this.from, this.to)
    )

    private fun Skill.toListItem() = SkillListItem(
        id = this.id,
        title = this.name
    )

    private fun createAddressText(address: Address?): String = when {
        address == null || address.city.isEmpty() && address.country.isEmpty() -> ""
        address.city.isEmpty() -> address.country
        address.country.isEmpty() -> address.city
        else -> "${address.city}, ${address.country}"
    }

    private fun createFromToText(from: Date, to: Date?): String {
        val fromText = from.formatShortDate()
        val toText = to?.formatShortDate().orEmpty()
        val tillNow = resourceProvider.getString(R.string.label_till_now)
        return when (to) {
            null -> resourceProvider.getString(R.string.label_from_to, fromText, tillNow)
            else -> resourceProvider.getString(R.string.label_from_to, fromText, toText)
        }
    }
}