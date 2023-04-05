package com.petrs.smartlab.ui.fragments.main.analyzes.category_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.petrs.smartlab.databinding.ItemCategoryBinding
import com.petrs.smartlab.ui.base.BaseListAdapter
import com.petrs.smartlab.ui.fragments.main.analyzes.models.Category

class CategoryAdapter(
    private val eventListener: CategoryEventListener
) : BaseListAdapter<Category, CategoryViewHolder>(
    areItemsTheSameCompare = { oldItem, newItem ->
        oldItem.title == newItem.title
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
        ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        eventListener
    )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
        if (position == 0) holder.selectItem()
    }


}