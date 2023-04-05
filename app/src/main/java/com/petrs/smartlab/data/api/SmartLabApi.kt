package com.petrs.smartlab.data.api

import com.petrs.smartlab.data.DataResult
import com.petrs.smartlab.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface SmartLabApi {

    @POST("/api/sendCode")
    suspend fun sendCode(
        @Header("email") email: String
    ): Response<MessageDTO>

    @POST("/api/signin")
    suspend fun signIn(
        @Header("email") email: String,
        @Header("code") code: String
    ): Response<TokenDTO>

    @POST("/api/createProfile")
    suspend fun createProfile(
        @Header("Authorization") token: String,
        @Body requestBody: CreateProfileBody
    ): Response<ProfileInfoDTO>

    @GET("/api/catalog")
    suspend fun getCatalog(): Response<List<CatalogItemDTO>>

    @GET("/api/news")
    suspend fun getNews(): Response<List<NewsItemDTO>>

    @PUT("/api/updateProfile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body requestBody: UpdateProfileBody
    ): Response<ProfileInfoDTO>

    @POST("/api/order")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Body requestBody: CreateOrderRequestBody
    ): Response<CreateOrderDTO>
}