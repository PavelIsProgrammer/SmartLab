package com.petrs.smartlab.domain.useCases

import com.petrs.smartlab.R
import com.petrs.smartlab.domain.mappers.toDomain
import com.petrs.smartlab.domain.models.ProfileInfoDomain
import com.petrs.smartlab.domain.repository.SharedPreferencesRepository
import com.petrs.smartlab.utils.network.State

class GetProfileUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    operator fun invoke(): State<List<ProfileInfoDomain>> = try {
        State.Success(sharedPreferencesRepository.getProfile().map { it.toDomain() })
    } catch (e: Exception) {
        State.Error(R.string.network_error)
    }
}