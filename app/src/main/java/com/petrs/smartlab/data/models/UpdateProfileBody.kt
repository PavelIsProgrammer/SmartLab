package com.petrs.smartlab.data.models

import com.google.gson.annotations.SerializedName

data class UpdateProfileBody(
    @SerializedName("id")
    val id: Int,
    @SerializedName("firstname")
    val firstName: String,
    @SerializedName("lastname")
    val lastName: String,
    @SerializedName("middlename")
    val midName: String,
    @SerializedName("bith")
    val birth: String,
    @SerializedName("pol")
    val sexOrientation: String
)