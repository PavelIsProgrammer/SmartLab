package com.petrs.smartlab.data.models

import com.google.gson.annotations.SerializedName

data class CreateOrderDTO(
    @SerializedName("order_id")
    val orderId: Int
)
