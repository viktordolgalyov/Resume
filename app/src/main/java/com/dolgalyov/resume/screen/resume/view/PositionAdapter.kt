package com.dolgalyov.resume.screen.resume.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dolgalyov.resume.R
import com.dolgalyov.resume.screen.resume.presentation.model.PositionListItem
import kotlinx.android.synthetic.main.item_position.view.*

class PositionAdapter :
    ListAdapter<PositionListItem, PositionAdapter.PositionHolder>(PositionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return PositionHolder(view)
    }

    override fun onBindViewHolder(holder: PositionHolder, position: Int) = with(holder.itemView) {
        val item = getItem(position)
        title.text = item.title
        description.isVisible = item.description.isNotBlank()
        description.text = item.description
        address.isVisible = item.address.isNotBlank()
        address.text = item.address
        company.text = item.company
        fromTo.text = item.fromTo
    }

    override fun getItemViewType(position: Int) = R.layout.item_position

    class PositionHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}