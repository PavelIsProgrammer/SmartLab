package com.petrs.smartlab.ui.fragments.main.order_register.models

import android.os.Parcelable
import com.petrs.smartlab.domain.models.ProfileInfoDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatientItem(
    val profile: ProfileInfoDomain,
    var selected: Boolean
): Parcelable
