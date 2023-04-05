package com.petrs.smartlab.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatalogItemDomain(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val category: String,
    val timeResult: String,
    val preparation: String,
    val bio: String,
    var inCart: Int = 0
): Parcelable
