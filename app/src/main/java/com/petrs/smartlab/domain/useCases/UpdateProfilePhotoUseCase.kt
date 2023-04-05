package com.petrs.smartlab.domain.useCases

import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.mappers.toDomainResult
import com.petrs.smartlab.domain.repository.SharedPreferencesRepository
import com.petrs.smartlab.domain.repository.SmartLabRepository
import com.petrs.smartlab.utils.getMimeType
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UpdateProfilePhotoUseCase(
    private val repository: SmartLabRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    suspend operator fun invoke(file: File): DomainResult<Unit> = repository.updateProfilePhoto(
        token = sharedPreferencesRepository.getToken(),
        file = file.asRequestBody(getMimeType(file.path).toMediaTypeOrNull()),
        type = getMimeType(file.path)
    ).toDomainResult { }
}