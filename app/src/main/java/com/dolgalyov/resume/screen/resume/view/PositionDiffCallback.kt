package com.dolgalyov.resume.screen.resume.view

import androidx.recyclerview.widget.DiffUtil
import com.dolgalyov.resume.screen.resume.presentation.model.PositionListItem

class PositionDiffCallback : DiffUtil.ItemCallback<PositionListItem>() {
    override fun areItemsTheSame(
        oldItem: PositionListItem,
        newItem: PositionListItem
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: PositionListItem,
        newItem: PositionListItem
    ): Boolean = oldItem == newItem
}