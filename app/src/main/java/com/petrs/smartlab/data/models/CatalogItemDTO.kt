package com.petrs.smartlab.data.models

import com.google.gson.annotations.SerializedName

data class CatalogItemDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("time_result")
    val timeResult: String,
    @SerializedName("preparation")
    val preparation: String,
    @SerializedName("bio")
    val bio: String,
    val inCart: Int = 0
)
