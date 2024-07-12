package com.swm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swm.domain.model.Content
import com.swm.domain.model.ViewType
import com.swm.presentation.databinding.ViewPlusTitleSectionBinding
import com.swm.presentation.databinding.ViewTitleSectionBinding
import com.swm.presentation.databinding.ViewUnknownSectionBinding
import com.swm.presentation.viewholder.SectionViewHolder

class HomeContentAdapter : RecyclerView.Adapter<SectionViewHolder>() {
    private var contents: List<Content> = listOf()

    fun setContents(contents: List<Content>) {
        this.contents = contents
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return contents[position].sectionComponentType.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val viewTypeEnum = ViewType.entries[viewType]

        return when (viewTypeEnum) {
            ViewType.TITLE -> SectionViewHolder.TitleViewHolder(
                ViewTitleSectionBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            ViewType.PLUS_TITLE -> SectionViewHolder.PlusTitleViewHolder(
                ViewPlusTitleSectionBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> SectionViewHolder.UnknownViewHolder(
                ViewUnknownSectionBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.bind(contents[position].section)
    }

    override fun getItemCount(): Int = contents.size
}