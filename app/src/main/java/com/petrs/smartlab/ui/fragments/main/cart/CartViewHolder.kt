package com.petrs.smartlab.ui.fragments.main.cart

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.ItemCartBinding
import com.petrs.smartlab.domain.models.CatalogItemDomain

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val eventListener: CartEventListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CatalogItemDomain) {
        binding.apply {
            root.background = ContextCompat.getDrawable(root.context, R.drawable.card_analysis_background)

            tvTitle.text = item.title
            tvPrice.text = root.context.getString(R.string.price_rub, item.price)
            tvQuantity.text = formatQuantity(item.inCart)

            btnAdd.setOnClickListener {
                item.inCart++
                tvQuantity.text = formatQuantity(item.inCart)
                eventListener.onAddClicked(item)
            }
            btnSubscription.setOnClickListener {
                item.inCart--
                tvQuantity.text = formatQuantity(item.inCart)
                eventListener.onSubscriptionClicked(item)
            }
            btnDelete.setOnClickListener {
                item.inCart = 0
                tvQuantity.text = formatQuantity(item.inCart)
                eventListener.onSubscriptionClicked(item)
            }
        }
    }

    private fun formatQuantity(quantity: Int): String {
        var result = ""
        result = if (quantity % 10 == 1 && quantity != 11) {
            "$quantity пациент"
        } else if (quantity % 10 in 2..4 && quantity !in 12..14) {
            "$quantity пациента"
        } else {
            "$quantity пациентов"
        }
        return result
    }
}