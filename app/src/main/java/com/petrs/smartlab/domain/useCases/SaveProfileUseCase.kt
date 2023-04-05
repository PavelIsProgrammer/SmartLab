package com.petrs.smartlab.domain.useCases

import com.petrs.smartlab.R
import com.petrs.smartlab.domain.mappers.toDTO
import com.petrs.smartlab.domain.models.ProfileInfoDomain
import com.petrs.smartlab.domain.repository.SharedPreferencesRepository
import com.petrs.smartlab.utils.network.State

class SaveProfileUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    operator fun invoke(profiles: List<ProfileInfoDomain>): State<Unit> = try {
        State.Success(sharedPreferencesRepository.saveProfile(profiles.map { it.toDTO() } ))
    } catch (e: Exception) {
        State.Error(R.string.network_error)
    }
}