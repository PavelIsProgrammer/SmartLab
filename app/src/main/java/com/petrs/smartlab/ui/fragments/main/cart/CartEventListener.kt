package com.petrs.smartlab.ui.fragments.main.cart

import com.petrs.smartlab.domain.models.CatalogItemDomain

interface CartEventListener {
    fun onAddClicked(item: CatalogItemDomain)
    fun onSubscriptionClicked(item: CatalogItemDomain)
}