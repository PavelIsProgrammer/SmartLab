package com.petrs.smartlab.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileInfoDomain(
    val id: Int,
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val midName: String,
    val birth: String,
    val sexOrientation: String,
    var image: String?
): Parcelable
