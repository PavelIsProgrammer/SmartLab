package com.petrs.smartlab.domain.mappers

import com.petrs.smartlab.data.DataResult
import com.petrs.smartlab.domain.DomainResult

fun <Data, Domain> DataResult<Data>.toDomainResult(transformation: (Data) -> Domain) = when (this) {
    is DataResult.Success -> DomainResult.Success(transformation(data))
    is DataResult.Error -> DomainResult.Error(type = errorType, errors = errors)
}