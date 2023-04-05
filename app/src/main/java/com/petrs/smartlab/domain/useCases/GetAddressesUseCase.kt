package com.petrs.smartlab.domain.useCases

import com.petrs.smartlab.R
import com.petrs.smartlab.domain.mappers.toDomain
import com.petrs.smartlab.domain.models.AddressDomain
import com.petrs.smartlab.domain.models.ProfileInfoDomain
import com.petrs.smartlab.domain.repository.SharedPreferencesRepository
import com.petrs.smartlab.utils.network.State

class GetAddressesUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    operator fun invoke(): State<List<AddressDomain>> = try {
        State.Success(sharedPreferencesRepository.getAddresses().map { it.toDomain() })
    } catch (e: Exception) {
        State.Error(R.string.network_error)
    }
}