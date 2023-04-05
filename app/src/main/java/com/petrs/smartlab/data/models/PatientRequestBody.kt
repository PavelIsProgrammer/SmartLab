package com.petrs.smartlab.data.models

import com.google.gson.annotations.SerializedName

data class PatientRequestBody(
    @SerializedName("name")
    val name: String,
    @SerializedName("items")
    val items: List<CatalogItemRequestBody>
)
