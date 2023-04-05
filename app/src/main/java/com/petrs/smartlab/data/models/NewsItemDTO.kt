package com.petrs.smartlab.data.models

import com.google.gson.annotations.SerializedName

data class NewsItemDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("image")
    val image: String
)
