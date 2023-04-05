package com.petrs.smartlab.domain.useCases

import com.petrs.smartlab.R
import com.petrs.smartlab.domain.repository.SharedPreferencesRepository
import com.petrs.smartlab.utils.network.State

class GetTokenUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    operator fun invoke(): State<String> = try {
        State.Success(sharedPreferencesRepository.getToken())
    } catch (e: Exception) {
        State.Error(R.string.network_error)
    }
}