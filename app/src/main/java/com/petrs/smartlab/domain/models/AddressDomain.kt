package com.petrs.smartlab.domain.models

data class AddressDomain(
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