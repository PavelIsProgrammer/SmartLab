package com.petrs.smartlab.domain.useCases

import com.petrs.smartlab.R
import com.petrs.smartlab.domain.repository.SharedPreferencesRepository
import com.petrs.smartlab.utils.network.State

class SaveAppPasswordUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    operator fun invoke(password: String): State<Unit> = try {
        State.Success(sharedPreferencesRepository.saveAppPassword(password))
    } catch (e: Exception) {
        State.Error(R.string.network_error)
    }
}