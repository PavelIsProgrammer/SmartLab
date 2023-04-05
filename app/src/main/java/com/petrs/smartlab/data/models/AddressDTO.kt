package com.petrs.smartlab.data.models

data class AddressDTO(
    val title: String,
    val address: String,
    val longitude: Double,
    val latitude: Double,
    val altitude: Double,
    val room: String,
    val entrance: String,
    val floor: String,
    val intercom: String
) {
}