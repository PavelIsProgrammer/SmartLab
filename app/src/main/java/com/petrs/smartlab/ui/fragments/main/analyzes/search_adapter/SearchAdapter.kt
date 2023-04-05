package com.petrs.smartlab.ui.fragments.main.analyzes.search_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.petrs.smartlab.databinding.ItemSearchResultBinding
import com.petrs.smartlab.domain.models.CatalogItemDomain
import com.petrs.smartlab.ui.base.BaseListAdapter

class SearchAdapter(
    private val selectedString: String,
    private val eventListener: SearchEventListener
) : BaseListAdapter<CatalogItemDomain, SearchViewHolder>(
    areItemsTheSameCompare = { oldItem, newItem ->
        oldItem.id == newItem.id
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchViewHolder(
        ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ), selectedString, eventListener
    )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}