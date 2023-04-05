package com.petrs.smartlab.ui.base

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<Item: Any, ViewHolder: RecyclerView.ViewHolder>(
    areItemsTheSameCompare: (oldItem: Item, newItem: Item) -> Boolean
): ListAdapter<Item, ViewHolder>(BaseItemCallback<Item>(areItemsTheSameCompare))