package com.petrs.smartlab.ui.fragments.main.analyzes.news_adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.ItemStockOrNewsBinding
import com.petrs.smartlab.domain.models.NewsItemDomain

class NewsViewHolder(
    private val binding: ItemStockOrNewsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NewsItemDomain) {
        binding.apply {
            tvTitle.text = item.title

            Glide.with(ivPhoto)
                .load(item.image)
                .into(ivPhoto)

            tvPrice.text = root.context.getString(R.string.price_rub, item.price)
//            tvDescription.text = item.description
        }
    }
}