package com.petrs.smartlab.ui.fragments.main.analyzes.news_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.petrs.smartlab.databinding.ItemStockOrNewsBinding
import com.petrs.smartlab.domain.models.NewsItemDomain
import com.petrs.smartlab.ui.base.BaseListAdapter

class NewsAdapter : BaseListAdapter<NewsItemDomain, NewsViewHolder>(
    areItemsTheSameCompare = { oldItem, newItem ->
        oldItem.id == newItem.id
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsViewHolder(
        ItemStockOrNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}