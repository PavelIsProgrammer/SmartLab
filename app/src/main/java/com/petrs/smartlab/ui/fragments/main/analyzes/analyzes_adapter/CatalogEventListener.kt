package com.petrs.smartlab.ui.fragments.main.analyzes.analyzes_adapter

import com.petrs.smartlab.domain.models.CatalogItemDomain

interface CatalogEventListener {
    fun onAddClicked(analysis: CatalogItemDomain)
    fun onItemClicked(analysis: CatalogItemDomain)
}