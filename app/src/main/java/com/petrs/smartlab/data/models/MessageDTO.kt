package com.petrs.smartlab.data.models

import com.google.gson.annotations.SerializedName

data class MessageDTO(
    @SerializedName("message")
    val message: String
)
