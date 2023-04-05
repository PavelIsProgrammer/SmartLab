package com.petrs.smartlab.ui.fragments.main.analyzes.category_adapter

import com.petrs.smartlab.ui.fragments.main.analyzes.models.Category

interface CategoryEventListener {
    fun onCategoryClick(category: Category)
}