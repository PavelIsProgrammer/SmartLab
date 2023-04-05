package com.petrs.smartlab.data.models

import com.google.gson.annotations.SerializedName

data class CreateOrderRequestBody(
    @SerializedName("address")
    var address: String,
    @SerializedName("date_time")
    var dateTime: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("comment")
    var comment: String = "",
    @SerializedName("audio_comment")
    var audioComment: String = "",
    @SerializedName("patients")
    var patients: List<PatientRequestBody>
) {
}