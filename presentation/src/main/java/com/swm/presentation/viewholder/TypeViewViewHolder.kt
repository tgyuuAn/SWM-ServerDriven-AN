package com.swm.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.swm.domain.model.ContentVO
import com.swm.presentation.databinding.ViewATypeBinding
import com.swm.presentation.databinding.ViewBTypeBinding
import com.swm.presentation.databinding.ViewRichViewTypeBinding
import com.swm.presentation.databinding.ViewUnknownSectionBinding

sealed class TypeViewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(content: ContentVO)

    class ATypeViewHolder(
        private val binding: ViewATypeBinding
    ) : TypeViewViewHolder(binding.root) {
        override fun bind(content: ContentVO) {
            binding.aType = content as ContentVO.AViewType
        }
    }

    class BTypeViewHolder(
        private val binding: ViewBTypeBinding
    ) : TypeViewViewHolder(binding.root) {
        override fun bind(content: ContentVO) {
            binding.bType = content as ContentVO.BViewType
        }
    }

    class RichViewTypeViewHolder(
        private val binding: ViewRichViewTypeBinding
    ) : TypeViewViewHolder(binding.root) {

        override fun bind(content: ContentVO) {
            binding.richViewType = content as ContentVO.RichViewType
        }
    }

    class UnknownViewHolder(
        private val binding: ViewUnknownSectionBinding
    ) : TypeViewViewHolder(binding.root) {
        override fun bind(content: ContentVO) {
            // Handle unknown content types or do nothing
        }
    }
}