package com.swm.presentation.viewholder

import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.swm.domain.model.Section
import com.swm.presentation.adapter.HomeTitleBadgeAdapter
import com.swm.presentation.databinding.ViewPlusTitleSectionBinding
import com.swm.presentation.databinding.ViewTitleSectionBinding
import com.swm.presentation.databinding.ViewUnknownSectionBinding

sealed class SectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(content: Section)

    class TitleViewHolder(
        private val binding: ViewTitleSectionBinding
    ) : SectionViewHolder(binding.root) {
        override fun bind(content: Section) {
            content as Section.TitleSection
            binding.textTitle.text = content.title

            val homeTitleBadgeAdapter = HomeTitleBadgeAdapter()
            homeTitleBadgeAdapter.setContents(content.badges)

            binding.recyclerBadge.apply {
                adapter = homeTitleBadgeAdapter
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }

            binding.textDescription.text = content.description
        }
    }

    class PlusTitleViewHolder(
        private val binding: ViewPlusTitleSectionBinding
    ) : SectionViewHolder(binding.root) {
        override fun bind(content: Section) {
            content as Section.PlusTitleSection
            binding.apply {
                imageFirstRow.apply {
                    load(content.firstRowImage.imgUrl)
                    layoutParams.width = content.firstRowImage.width.toInt()
                    layoutParams.height = content.firstRowImage.height.toInt()
                }

                val textStyle = content.titleText.textStyle
                val typeface = when {
                    textStyle.contains("bold") && textStyle.contains("italic") -> Typeface.BOLD_ITALIC
                    textStyle.contains("bold") -> Typeface.BOLD
                    textStyle.contains("italic") -> Typeface.ITALIC
                    else -> Typeface.NORMAL
                }

                textTitle.apply {
                    text = content.titleText.text
                    textSize = content.titleText.textSize.toFloat()
                    setTextColor(Color.parseColor(content.titleText.textColor))
                    setTypeface(null, typeface)
                }

                val homeTitleBadgeAdapter = HomeTitleBadgeAdapter()
                homeTitleBadgeAdapter.setContents(content.badges)

                recyclerBadge.apply {
                    adapter = homeTitleBadgeAdapter
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                }

                textDescription.text = content.description
            }
        }
    }

    class UnknownViewHolder(
        private val binding: ViewUnknownSectionBinding
    ) : SectionViewHolder(binding.root) {
        override fun bind(content: Section) {
            // Handle unknown content types or do nothing
        }
    }
}