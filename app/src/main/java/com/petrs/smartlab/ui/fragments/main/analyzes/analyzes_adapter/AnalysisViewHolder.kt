package com.petrs.smartlab.ui.fragments.main.analyzes.analyzes_adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.petrs.smartlab.R
import com.petrs.smartlab.databinding.ItemAnalysisBinding
import com.petrs.smartlab.domain.models.CatalogItemDomain

class AnalysisViewHolder(
    private val binding: ItemAnalysisBinding,
    private val eventListener: CatalogEventListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CatalogItemDomain) {
        binding.apply {
            root.background = ContextCompat.getDrawable(root.context, R.drawable.card_analysis_background)

            root.setOnClickListener {
                eventListener.onItemClicked(item)
            }

            btnAdd.setOnClickListener {
                if (item.inCart > 0) {
                    deleteFromCart()
                    item.inCart--
                } else {
                    addInCart()
                    item.inCart++
                }
                eventListener.onAddClicked(item)
            }

            tvTitle.text = item.title
            tvPrice.text = root.context.getString(R.string.price_rub, item.price)
            tvDescription.text = item.timeResult

            if (item.inCart > 0) addInCart() else deleteFromCart()
        }
    }

    private fun addInCart() {
        binding.apply {
            btnAdd.background = ContextCompat.getDrawable(root.context, R.drawable.outlined_btn)
            btnAdd.setTextColor(ContextCompat.getColor(root.context, R.color.second_blue))
            btnAdd.text = "Убрать"
        }
    }

    private fun deleteFromCart() {
        binding.apply {
            btnAdd.background = ContextCompat.getDrawable(root.context, R.drawable.filled_btn)
            btnAdd.setTextColor(ContextCompat.getColor(root.context, R.color.white))
            btnAdd.text = "Добавить"
        }
    }
}