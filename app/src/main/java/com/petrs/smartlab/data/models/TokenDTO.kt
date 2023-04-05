package com.petrs.smartlab.data.models

import com.google.gson.annotations.SerializedName

data class TokenDTO(
    @SerializedName("token")
    val token: String
)
