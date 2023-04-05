package com.petrs.smartlab.data.models

import com.google.gson.annotations.SerializedName

data class CreateProfileBody(
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
