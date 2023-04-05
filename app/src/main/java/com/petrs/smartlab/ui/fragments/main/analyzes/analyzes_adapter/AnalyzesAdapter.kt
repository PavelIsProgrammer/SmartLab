package com.petrs.smartlab.ui.fragments.main.analyzes.analyzes_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.petrs.smartlab.databinding.ItemAnalysisBinding
import com.petrs.smartlab.domain.models.CatalogItemDomain
import com.petrs.smartlab.ui.base.BaseListAdapter

class AnalyzesAdapter(
    private val eventListener: CatalogEventListener
) : BaseListAdapter<CatalogItemDomain, AnalysisViewHolder>(
    areItemsTheSameCompare = { oldItem, newItem ->
        oldItem.id == newItem.id
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AnalysisViewHolder(
        ItemAnalysisBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        eventListener
    )

    override fun onBindViewHolder(holder: AnalysisViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}