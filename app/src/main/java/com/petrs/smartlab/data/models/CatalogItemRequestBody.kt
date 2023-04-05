package com.petrs.smartlab.data.models

import com.google.gson.annotations.SerializedName

data class CatalogItemRequestBody(
    @SerializedName("catalog_id")
    val id: Int,
    @SerializedName("price")
    val price: String
)
