package com.petrs.smartlab.domain.repository

import com.petrs.smartlab.data.DataResult
import com.petrs.smartlab.data.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface SmartLabRepository {

    suspend fun sendCode(email: String): DataResult<MessageDTO>

    suspend fun signIn(email: String, code: String): DataResult<TokenDTO>

    suspend fun createProfile(token: String, profileBody: CreateProfileBody): DataResult<ProfileInfoDTO>

    suspend fun getCatalog(): DataResult<List<CatalogItemDTO>>

    suspend fun getNews(): DataResult<List<NewsItemDTO>>

    suspend fun updateProfile(token: String, profileBody: UpdateProfileBody): DataResult<ProfileInfoDTO>

    suspend fun createOrder(token: String, orderRequestBody: CreateOrderRequestBody): DataResult<CreateOrderDTO>

    suspend fun updateProfilePhoto(token: String, file: RequestBody, type: String): DataResult<Unit>
}