package com.swm.presentation

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.swm.domain.model.Content
import com.swm.domain.model.Section
import com.swm.domain.model.ViewType
import com.swm.presentation.databinding.ViewPlusTitleSectionBinding
import com.swm.presentation.databinding.ViewTitleSectionBinding
import com.swm.presentation.databinding.ViewUnknownSectionBinding

class HomeContentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var contents: List<Content> = listOf()

    fun setContents(contents: List<Content>) {
        this.contents = contents
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) : Int {
        return contents[position].sectionComponentType.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewTypeEnum = ViewType.values()[viewType]

        return when(viewTypeEnum) {
            ViewType.TITLE -> TitleViewHolder(ViewTitleSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            ViewType.PLUS_TITLE -> PlusTitleViewHolder(ViewPlusTitleSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> UnknownViewHolder(ViewUnknownSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        when (holder) {
            is TitleViewHolder -> holder.bind(contents[position].section as Section.TitleSection)
            is PlusTitleViewHolder -> holder.bind(contents[position].section as Section.PlusTitleSection)
            is UnknownViewHolder -> holder.bind(contents[position].section as Section.UnKnownSection)
        }
    }

    override fun getItemCount(): Int = contents.size

    class TitleViewHolder(
        private val binding: ViewTitleSectionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(content: Section.TitleSection) {
            binding.textTitle.text = content.title

            val homeTitleBadgeAdapter = HomeTitleBadgeAdapter()
            homeTitleBadgeAdapter.setContents(content.badges)
            binding.recyclerBadge.apply {
                adapter = homeTitleBadgeAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

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

            val textStyle = content.titleText.textStyle

            val typeface = when {
                textStyle.contains("bold") && textStyle.contains("italic") -> Typeface.BOLD_ITALIC
                textStyle.contains("bold") -> Typeface.BOLD
                textStyle.contains("italic") -> Typeface.ITALIC
                else -> Typeface.NORMAL
            }

            binding.textTitle.setTypeface(null, typeface)

            // if(content.titleText.textStyle == "strike")

            val homeTitleBadgeAdapter = HomeTitleBadgeAdapter()
            homeTitleBadgeAdapter.setContents(content.badges)
            binding.recyclerBadge.apply {
                adapter = homeTitleBadgeAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

            binding.textDescription.text = content.description
        }
    }

    class UnknownViewHolder(
        private val binding: ViewUnknownSectionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(content: Section.UnKnownSection) {

        }
    }
}