package com.petrs.smartlab.ui.fragments.main.analyzes.search_adapter

import com.petrs.smartlab.domain.models.CatalogItemDomain

interface SearchEventListener {
    fun onClick(item: CatalogItemDomain)
}