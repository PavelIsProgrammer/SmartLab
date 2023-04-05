package com.petrs.smartlab.data.repositoryImpl

import android.util.Log
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.petrs.smartlab.data.DataResult
import com.petrs.smartlab.data.ErrorType
import com.petrs.smartlab.data.api.SmartLabApi
import com.petrs.smartlab.data.models.*
import com.petrs.smartlab.domain.repository.SmartLabRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection

class SmartLabRepositoryImpl(private val api: SmartLabApi) : SmartLabRepository {

    override suspend fun sendCode(email: String) = execRequest { api.sendCode(email) }

    override suspend fun signIn(email: String, code: String) = execRequest { api.signIn(email, code) }

    override suspend fun createProfile(token: String, profileBody: CreateProfileBody) = execRequest {
        api.createProfile(token, profileBody)
    }


    override suspend fun getCatalog() = execRequest { api.getCatalog() }

    override suspend fun getNews() = execRequest { api.getNews() }

    override suspend fun updateProfile(
        token: String,
        profileBody: UpdateProfileBody
    ) = execRequest { api.updateProfile(token, profileBody) }

    override suspend fun updateProfilePhoto(
        token: String,
        file: RequestBody,
        type: String
    ): DataResult<Unit> = execRequest {
        api.updateProfilePhoto(token, file, type)
    }

    override suspend fun createOrder(
        token: String,
        orderRequestBody: CreateOrderRequestBody
    ): DataResult<CreateOrderDTO> = execRequest { api.createOrder(token, orderRequestBody) }

    private suspend inline fun <reified T> execRequest(
        request: () -> Response<T>
    ): DataResult<T> {
        return try {
            var response = request()

            if (response.isSuccessful)
                DataResult.Success(response.body() as T)
            else
                DataResult.Error(ErrorType.REQUEST_ERROR, getError(response))
        } catch (e: Exception) {
            e.localizedMessage?.let { Log.i("info", it) }
            when (e) {
                is IOException -> DataResult.Error(ErrorType.NO_INTERNET)
                else -> DataResult.Error(ErrorType.REQUEST_ERROR)
            }
        }
    }

    private fun getError(response: Response<*>): String {
        val adapter: TypeAdapter<ApiError> = Gson().getAdapter(ApiError::class.java)
        return adapter.fromJson(response.errorBody()?.string()).errors
    }
}