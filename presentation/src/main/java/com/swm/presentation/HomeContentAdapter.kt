package com.swm.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swm.domain.ViewType
import com.swm.domain.entity.ContentVO
import com.swm.domain.entity.ViewTypeVO

class HomeContentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var contents: List<ViewTypeVO> = listOf()

    fun setContents(contents: List<ViewTypeVO>) {
        this.contents = contents
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (contents[position].sectionComponentType) {
            ViewType.TITLE -> R.layout.view_title_section
            ViewType.PLUS_TITLE -> R.layout.view_plus_title_section
            else -> R.layout.view_unknown_section
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        return when(viewType) {
            R.layout.view_title_section -> TitleViewHolder(view)
            R.layout.view_plus_title_section -> PlusTitleViewHolder(view)
            else -> UnknownViewHolder(view)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        when (holder) {
            is TitleViewHolder -> holder.bind(contents[position].section)
            is PlusTitleViewHolder -> holder.bind(contents[position].section)
        }
    }

    override fun getItemCount(): Int = contents.size

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(content: ContentVO) {

        }
    }

    class PlusTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(content: ContentVO) {

        }
    }

    class UnknownViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}