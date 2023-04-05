package com.petrs.smartlab.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.petrs.smartlab.data.models.AddressDTO
import com.petrs.smartlab.data.models.CatalogItemDTO
import com.petrs.smartlab.data.models.ProfileInfoDTO

class SharedPreferencesHandler(context: Context) {

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun saveToken(token: String) =
        sharedPrefs.edit().putString(SharedPreferencesKeys.AuthorizationToken.name, "Bearer $token").apply()

    fun getToken() =
        sharedPrefs.getString(SharedPreferencesKeys.AuthorizationToken.name, null) ?: ""

    fun saveAppPassword(password: String) =
        sharedPrefs.edit().putString(SharedPreferencesKeys.AppPassword.name, password).apply()

    fun getAppPassword() =
        sharedPrefs.getString(SharedPreferencesKeys.AppPassword.name, null) ?: ""

    fun saveProfile(profile: List<ProfileInfoDTO>) =
        sharedPrefs.edit().putString(SharedPreferencesKeys.Profiles.name, Gson().toJson(profile)).apply()

    fun getProfile(): List<ProfileInfoDTO> {
        val res = sharedPrefs.getString(SharedPreferencesKeys.Profiles.name, null)
        val listType = object : TypeToken<List<ProfileInfoDTO>>(){}.type
        return if (res.isNullOrEmpty()) emptyList() else Gson().fromJson(res, listType)
    }

    fun saveCart(cart: List<CatalogItemDTO>) =
        sharedPrefs.edit().putString(SharedPreferencesKeys.Cart.name, Gson().toJson(cart)).apply()

    fun getCart(): List<CatalogItemDTO> {
        val res = sharedPrefs.getString(SharedPreferencesKeys.Cart.name, null)
        val listType = object : TypeToken<List<CatalogItemDTO>>(){}.type
        return if (res.isNullOrEmpty()) emptyList() else Gson().fromJson(res, listType)
    }

    fun saveAddresses(addresses: List<AddressDTO>) =
        sharedPrefs.edit().putString(SharedPreferencesKeys.Addresses.name, Gson().toJson(addresses)).apply()

    fun getAddresses(): List<AddressDTO> {
        val res = sharedPrefs.getString(SharedPreferencesKeys.Addresses.name, null)
        val listType = object : TypeToken<List<AddressDTO>>(){}.type
        return if (res.isNullOrEmpty()) emptyList() else Gson().fromJson(res, listType)
    }
}