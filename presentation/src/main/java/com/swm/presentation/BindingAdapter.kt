package com.swm.presentation

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.swm.domain.model.RichText

object BindingAdapter {

    @BindingAdapter("image")
    @JvmStatic
    fun loadImage(imageView: ImageView, img: String) {
        imageView.load(img)
    }

    @BindingAdapter("richText")
    @JvmStatic
    fun setSpanText(textView: TextView, richTexts: List<RichText>) {
        val context = textView.context
        val spannableText = SpannableStringBuilder()

        richTexts.forEach { richText ->
            Log.d("test", richText.toString())

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
                        val request = ImageRequest.Builder(context)
                            .data(it)
                            .target { drawable ->
                                drawable.setBounds(
                                    0,
                                    0,
                                    imageContent.width,
                                    imageContent.height
                                )

                                val start = spannableText.length
                                spannableText.append("1")
                                val end = spannableText.length

                                // Drawable을 사용하여 ImageSpan 생성
                                val imageSpan = ImageSpan(drawable)
                                spannableText.setSpan(
                                    imageSpan,
                                    start,
                                    end,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                )

                                textView.post {
                                    textView.text = spannableText
                                }
                            }.build()

                        context.imageLoader.enqueue(request)
                    }
                }
            }
        }

        textView.text = spannableText
    }
}