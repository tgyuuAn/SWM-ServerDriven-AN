package com.swm.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swm.domain.model.ContentItemVO
import com.swm.domain.model.ViewType
import com.swm.presentation.databinding.ViewATypeBinding
import com.swm.presentation.databinding.ViewBTypeBinding
import com.swm.presentation.databinding.ViewRichViewTypeBinding
import com.swm.presentation.databinding.ViewUnknownSectionBinding
import com.swm.presentation.viewholder.TypeViewViewHolder

class HomeTypeViewAdapter : RecyclerView.Adapter<TypeViewViewHolder>() {
    private var contents: List<ContentItemVO> = listOf()

    init {
        Log.d("test", "TypeViewAdapter 생성")
    }

    fun setContents(contents: List<ContentItemVO>) {
        this.contents = contents
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return contents[position].viewType.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewViewHolder {
        val viewTypeEnum = ViewType.entries[viewType]

        Log.d("test", "CreateViewHolder")

        return when (viewTypeEnum) {
            ViewType.AViewType -> TypeViewViewHolder.ATypeViewHolder(
                ViewATypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            ViewType.BViewType -> TypeViewViewHolder.BTypeViewHolder(
                ViewBTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            ViewType.RichViewType -> TypeViewViewHolder.RichViewTypeViewHolder(
                ViewRichViewTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> TypeViewViewHolder.UnknownViewHolder(
                ViewUnknownSectionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: TypeViewViewHolder, position: Int) {
        Log.d("test", "BindViewHolder")

        holder.bind(contents[position].content)
    }

    override fun getItemCount(): Int = contents.size
}