package com.swm.presentation

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.swm.domain.model.Content
import com.swm.domain.model.Section
import com.swm.domain.model.ViewType
import com.swm.presentation.databinding.ViewPlusTitleSectionBinding
import com.swm.presentation.databinding.ViewTitleSectionBinding

class HomeContentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var contents: List<Content> = listOf()

    fun setContents(contents: List<Content>) {
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
            R.layout.view_title_section -> TitleViewHolder(ViewTitleSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.view_plus_title_section -> PlusTitleViewHolder(ViewPlusTitleSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> UnknownViewHolder(view)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        when (holder) {
            is TitleViewHolder -> holder.bind(contents[position].section as Section.TitleSection)
            is PlusTitleViewHolder -> holder.bind(contents[position].section as Section.PlusTitleSection)
        }
    }

    override fun getItemCount(): Int = contents.size

    class TitleViewHolder(
        private val binding: ViewTitleSectionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(content: Section.TitleSection) {
            binding.textTitle.text = content.title
            // binding.recyclerBadge.adapter =
            binding.textDescription.text = content.description
        }
    }

    class PlusTitleViewHolder(
        private val binding: ViewPlusTitleSectionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(content: Section.PlusTitleSection) {
            binding.imageFirstRow.load(content.firstRowImage.imgUrl)
            binding.imageFirstRow.layoutParams.width = content.firstRowImage.width.toInt()
            binding.imageFirstRow.layoutParams.height = content.firstRowImage.height.toInt()

            binding.textTitle.text = content.titleText.text
            binding.textTitle.textSize = content.titleText.textSize.toFloat()
            binding.textTitle.setTextColor(Color.parseColor(content.titleText.textColor))
            if(content.titleText.textStyle == "bold") binding.textTitle.setTypeface(null, Typeface.BOLD)
            if(content.titleText.textStyle == "italic") binding.textTitle.setTypeface(null, Typeface.ITALIC)
            // if(content.titleText.textStyle == "strike")

            // binding.recyclerBadge.adapter =

            binding.textDescription.text = content.description
        }
    }

    class UnknownViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}