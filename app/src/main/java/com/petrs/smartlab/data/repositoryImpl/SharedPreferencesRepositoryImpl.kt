package com.petrs.smartlab.data.repositoryImpl

import com.petrs.smartlab.data.models.AddressDTO
import com.petrs.smartlab.data.models.CatalogItemDTO
import com.petrs.smartlab.data.models.ProfileInfoDTO
import com.petrs.smartlab.data.sharedPreferences.SharedPreferencesHandler
import com.petrs.smartlab.domain.repository.SharedPreferencesRepository

class SharedPreferencesRepositoryImpl(private val handler: SharedPreferencesHandler) :
    SharedPreferencesRepository {

    override fun changeOnboardingStatus(status: Boolean) = handler.changeOnboardingStatus(status)

    override fun getOnboardingStatus(): Boolean = handler.getOnboardingStatus()

    override fun saveToken(token: String) = handler.saveToken(token)

    override fun getToken(): String = handler.getToken()

    override fun saveAppPassword(password: String) = handler.saveAppPassword(password)

    override fun getAppPassword(): String = handler.getAppPassword()

    override fun saveProfile(profiles: List<ProfileInfoDTO>) = handler.saveProfile(profiles)

    override fun getProfile(): List<ProfileInfoDTO> = handler.getProfile()

    override fun saveCart(cart: List<CatalogItemDTO>) = handler.saveCart(cart)

    override fun getCart(): List<CatalogItemDTO> = handler.getCart()

    override fun saveAddresses(addresses: List<AddressDTO>) = handler.saveAddresses(addresses)

    override fun getAddresses(): List<AddressDTO> = handler.getAddresses()

}