package com.petrs.smartlab.ui.fragments.start.onboarding.models

import androidx.annotation.DrawableRes

data class OnboardingData(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)
