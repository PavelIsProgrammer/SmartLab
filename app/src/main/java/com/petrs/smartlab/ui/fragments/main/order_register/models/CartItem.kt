package com.petrs.smartlab.ui.fragments.main.order_register.models

import com.petrs.smartlab.domain.models.CatalogItemDomain

data class CartItem(
    val catalogItem: CatalogItemDomain,
    var selected: Boolean
) {
}