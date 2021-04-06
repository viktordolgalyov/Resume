package com.dolgalyov.resume.screen.resume.view

import androidx.recyclerview.widget.DiffUtil
import com.dolgalyov.resume.screen.resume.presentation.model.EducationListItem

class EducationDiffCallback : DiffUtil.ItemCallback<EducationListItem>() {

    override fun areItemsTheSame(
        oldItem: EducationListItem,
        newItem: EducationListItem
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: EducationListItem,
        newItem: EducationListItem
    ): Boolean = oldItem == newItem
}