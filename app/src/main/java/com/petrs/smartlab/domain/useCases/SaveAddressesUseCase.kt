package com.petrs.smartlab.domain.useCases

import com.petrs.smartlab.R
import com.petrs.smartlab.domain.mappers.toDTO
import com.petrs.smartlab.domain.models.AddressDomain
import com.petrs.smartlab.domain.models.ProfileInfoDomain
import com.petrs.smartlab.domain.repository.SharedPreferencesRepository
import com.petrs.smartlab.utils.network.State

class SaveAddressesUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    operator fun invoke(addresses: List<AddressDomain>): State<Unit> = try {
        State.Success(sharedPreferencesRepository.saveAddresses( addresses.map { it.toDTO() } ))
    } catch (e: Exception) {
        State.Error(R.string.network_error)
    }
}