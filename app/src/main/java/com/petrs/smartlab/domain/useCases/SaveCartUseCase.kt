package com.petrs.smartlab.domain.useCases

import com.petrs.smartlab.R
import com.petrs.smartlab.domain.mappers.toDTO
import com.petrs.smartlab.domain.models.CatalogItemDomain
import com.petrs.smartlab.domain.repository.SharedPreferencesRepository
import com.petrs.smartlab.utils.network.State
import org.koin.core.context.startKoin

class SaveCartUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    operator fun invoke(cart: List<CatalogItemDomain>): State<Unit> = try {
        State.Success(sharedPreferencesRepository.saveCart(cart.map { it.toDTO() }))
    } catch (e: Exception) {
        State.Error(R.string.network_error)
    }
}