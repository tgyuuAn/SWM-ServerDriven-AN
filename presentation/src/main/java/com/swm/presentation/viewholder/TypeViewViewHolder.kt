package com.swm.presentation.viewholder

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
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

            val context = binding.root.context
            val spannableText = SpannableStringBuilder()

            content.richText.forEach { richText ->
                when {
                    richText.text != null -> {
                        val textContent = richText.text
                        val start = spannableText.length
                        spannableText.append(textContent?.text)
                        val end = spannableText.length

                        textContent?.fontSize?.let {
                            val absoluteSizeSpan = AbsoluteSizeSpan(it, true) // true to use dp
                            spannableText.setSpan(
                                absoluteSizeSpan,
                                start,
                                end,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }

                        textContent?.textColor?.let {
                            val color = Color.parseColor(it)
                            val foregroundSpan = ForegroundColorSpan(color)
                            spannableText.setSpan(
                                foregroundSpan,
                                start,
                                end,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }

                        textContent?.textStyle?.forEach { style ->
                            when (style) {
                                "bold" -> spannableText.setSpan(
                                    StyleSpan(Typeface.BOLD),
                                    start,
                                    end,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                )

                                "italic" -> spannableText.setSpan(
                                    StyleSpan(Typeface.ITALIC),
                                    start,
                                    end,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                )

                                "underline" -> spannableText.setSpan(
                                    UnderlineSpan(),
                                    start,
                                    end,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                            }
                        }
                    }

                    richText.image != null -> {
                        val imageContent = richText.image
                        imageContent?.url?.toUri()?.let {
                            val start = spannableText.length
                            spannableText.append("\uFFFC") // Object replacement character
                            val end = spannableText.length

                            val request = ImageRequest.Builder(context)
                                .data(it) // demo link
                                .target { drawable ->
                                    drawable.setBounds(
                                        0, 0, imageContent.width, imageContent.height
                                    )

                                    val start = spannableText.length
                                    spannableText.append("1") // 이미지를 대체할 문자
                                    val end = spannableText.length

                                    // Drawable을 사용하여 ImageSpan 생성
                                    val imageSpan = ImageSpan(drawable)
                                    spannableText.setSpan(
                                        imageSpan,
                                        start,
                                        end,
                                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                    )

                                    binding.textRich.post {
                                        binding.textRich.text = spannableText
                                    }
                                }.build()

                            context.imageLoader.enqueue(request)
                        }
                    }
                }
            }
            binding.textRich.text = spannableText
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