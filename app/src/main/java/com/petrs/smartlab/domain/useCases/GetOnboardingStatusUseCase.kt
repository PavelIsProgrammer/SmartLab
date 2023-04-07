package com.petrs.smartlab.domain.useCases

import com.petrs.smartlab.data.ErrorType
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.repository.SharedPreferencesRepository

class GetOnboardingStatusUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    operator fun invoke(): DomainResult<Boolean> = try {
        DomainResult.Success(sharedPreferencesRepository.getOnboardingStatus())
    } catch (e: Exception) {
        DomainResult.Error(ErrorType.REQUEST_ERROR, "Непредвиденная ошибка")
    }
}