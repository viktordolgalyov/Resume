package com.dolgalyov.resume.screen.resume.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dolgalyov.resume.R
import com.dolgalyov.resume.screen.resume.presentation.model.EducationListItem
import kotlinx.android.synthetic.main.item_education_record.view.*

class EducationAdapter :
    ListAdapter<EducationListItem, EducationAdapter.EducationHolder>(EducationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return EducationHolder(view)
    }

    override fun onBindViewHolder(holder: EducationHolder, position: Int) = with(holder.itemView) {
        val item = getItem(position)
        title.text = item.university
        degree.text = item.degree
        address.text = item.address
        fromTo.text = item.fromTo
    }

    override fun getItemViewType(position: Int) = R.layout.item_education_record

    class EducationHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}