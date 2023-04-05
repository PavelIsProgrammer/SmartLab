package com.petrs.smartlab.domain.mappers

import com.petrs.smartlab.data.models.MessageDTO
import com.petrs.smartlab.data.models.ProfileInfoDTO
import com.petrs.smartlab.data.models.TokenDTO
import com.petrs.smartlab.domain.models.MessageDomain
import com.petrs.smartlab.domain.models.ProfileInfoDomain
import com.petrs.smartlab.domain.models.TokenDomain

fun MessageDTO.toDomain() =
    MessageDomain(
        message = message
    )

fun TokenDTO.toDomain() =
    TokenDomain(
        token = token
    )

fun ProfileInfoDTO.toDomain() =
    ProfileInfoDomain(
        id = id,
        userId = userId,
        firstName = firstName,
        lastName = lastName,
        midName = midName,
        birth = birth,
        sexOrientation = sexOrientation,
        image = image
    )

fun ProfileInfoDomain.toDTO() =
    ProfileInfoDTO(
        id = id,
        userId = userId,
        firstName = firstName,
        lastName = lastName,
        midName = midName,
        birth = birth,
        sexOrientation = sexOrientation,
        image = image
    )

