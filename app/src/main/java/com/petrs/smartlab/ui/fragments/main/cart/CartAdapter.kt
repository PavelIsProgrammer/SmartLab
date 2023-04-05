package com.petrs.smartlab.ui.fragments.main.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import com.petrs.smartlab.databinding.ItemCartBinding
import com.petrs.smartlab.domain.models.CatalogItemDomain
import com.petrs.smartlab.ui.base.BaseListAdapter

class CartAdapter(
    private val eventListener: CartEventListener
) : BaseListAdapter<CatalogItemDomain, CartViewHolder>(
    areItemsTheSameCompare = { oldItem, newItem ->
        oldItem.id == newItem.id
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CartViewHolder(
        ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false), eventListener
    )

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}