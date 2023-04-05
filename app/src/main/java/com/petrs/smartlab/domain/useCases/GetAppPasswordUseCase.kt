package com.petrs.smartlab.domain.useCases

import com.petrs.smartlab.R
import com.petrs.smartlab.domain.repository.SharedPreferencesRepository
import com.petrs.smartlab.utils.network.State

class GetAppPasswordUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    operator fun invoke(): State<String> = try {
        State.Success(sharedPreferencesRepository.getAppPassword())
    } catch (e: Exception) {
        State.Error(R.string.network_error)
    }
}