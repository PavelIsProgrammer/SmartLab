package com.petrs.smartlab.data.models

import com.google.gson.annotations.SerializedName
import com.petrs.smartlab.domain.models.ProfileInfoDomain

data class ProfileInfoDTO(
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("user_id")
    val userId: Int = -1,
    @SerializedName("firstname")
    val firstName: String = "",
    @SerializedName("lastname")
    val lastName: String = "",
    @SerializedName("middlename")
    val midName: String = "",
    @SerializedName("bith")
    val birth: String = "",
    @SerializedName("pol")
    val sexOrientation: String = "",
    @SerializedName("image")
    val image: String? = null
)

