package com.swm.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.swm.domain.model.Badge
import com.swm.presentation.databinding.ItemTitleBadgeBinding

class HomeTitleBadgeAdapter : RecyclerView.Adapter<HomeTitleBadgeAdapter.BadgeViewHolder>() {
    private var badges: List<Badge> = listOf()

    fun setContents(badges: List<Badge>) {
        this.badges = badges
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeViewHolder {
        return BadgeViewHolder(ItemTitleBadgeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BadgeViewHolder, position: Int) {
        holder.bind(badges[position])
    }

    override fun getItemCount(): Int = badges.size

    class BadgeViewHolder(
        private val binding: ItemTitleBadgeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Badge) {
            binding.textBadge.text = item.text
            binding.imageBadge.load(item.badgeImage)
        }
    }
}