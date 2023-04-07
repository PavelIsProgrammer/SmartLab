package com.petrs.smartlab.domain.repository

import android.location.Address
import com.petrs.smartlab.data.models.AddressDTO
import com.petrs.smartlab.data.models.CatalogItemDTO
import com.petrs.smartlab.data.models.ProfileInfoDTO

interface SharedPreferencesRepository {

    fun changeOnboardingStatus(status: Boolean)
    fun getOnboardingStatus(): Boolean

    fun saveToken(token: String)
    fun getToken(): String

    fun saveAppPassword(password: String)
    fun getAppPassword(): String

    fun saveProfile(profile: List<ProfileInfoDTO>)
    fun getProfile(): List<ProfileInfoDTO>

    fun saveCart(cart: List<CatalogItemDTO>)
    fun getCart(): List<CatalogItemDTO>

    fun saveAddresses(addresses: List<AddressDTO>)
    fun getAddresses(): List<AddressDTO>
}