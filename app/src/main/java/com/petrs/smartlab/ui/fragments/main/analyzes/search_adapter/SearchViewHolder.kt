package com.petrs.smartlab.ui.fragments.main.analyzes.search_adapter

import androidx.recyclerview.widget.RecyclerView
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.ItemSearchResultBinding
import com.petrs.smartlab.domain.models.CatalogItemDomain
import com.petrs.smartlab.utils.colorized

class SearchViewHolder(
    private val binding: ItemSearchResultBinding,
    private val selectedString: String,
    private val eventListener: SearchEventListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CatalogItemDomain) {
        binding.apply {
            tvTitle.text = colorized(root.context, item.title, selectedString, R.color.second_blue)
            tvPrice.text = root.context.getString(R.string.price_rub, item.price)
            tvTime.text = item.timeResult

            root.setOnClickListener {
                eventListener.onClick(item)
            }
        }
    }
}