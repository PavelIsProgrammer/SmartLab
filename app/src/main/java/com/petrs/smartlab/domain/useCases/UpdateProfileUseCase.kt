package com.petrs.smartlab.domain.useCases

import com.petrs.smartlab.data.models.UpdateProfileBody
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.mappers.toDomain
import com.petrs.smartlab.domain.mappers.toDomainResult
import com.petrs.smartlab.domain.models.ProfileInfoDomain
import com.petrs.smartlab.domain.repository.SharedPreferencesRepository
import com.petrs.smartlab.domain.repository.SmartLabRepository

class UpdateProfileUseCase(
    private val repository: SmartLabRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    suspend operator fun invoke(profileBody: UpdateProfileBody): DomainResult<ProfileInfoDomain> =
        repository.updateProfile(sharedPreferencesRepository.getToken(), profileBody)
            .toDomainResult { it.toDomain() }
}