package com.swm.presentation

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.swm.domain.model.RichText
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

object BindingAdapter {

    @BindingAdapter("image")
    @JvmStatic
    fun loadImage(imageView: ImageView, img: String) {
        imageView.load(img)
    }
}