package com.petrs.smartlab.domain.useCases

import com.petrs.smartlab.data.ErrorType
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.repository.SharedPreferencesRepository

class ChangeOnboardingStatusUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    operator fun invoke(status: Boolean): DomainResult<Unit> = try {
        DomainResult.Success(sharedPreferencesRepository.changeOnboardingStatus(status))
    } catch (e: Exception) {
        DomainResult.Error(ErrorType.REQUEST_ERROR, "Непредвиденная ошибка")
    }
}