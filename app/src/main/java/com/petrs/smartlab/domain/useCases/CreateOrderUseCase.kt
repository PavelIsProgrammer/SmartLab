package com.petrs.smartlab.domain.useCases

import com.petrs.smartlab.data.models.CreateOrderRequestBody
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.mappers.toDomain
import com.petrs.smartlab.domain.mappers.toDomainResult
import com.petrs.smartlab.domain.models.CreateOrderDomain
import com.petrs.smartlab.domain.repository.SharedPreferencesRepository
import com.petrs.smartlab.domain.repository.SmartLabRepository

class CreateOrderUseCase(
    private val repository: SmartLabRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    suspend operator fun invoke(orderRequestBody: CreateOrderRequestBody): DomainResult<CreateOrderDomain> =
        repository.createOrder(sharedPreferencesRepository.getToken(), orderRequestBody)
            .toDomainResult { it.toDomain() }
}
