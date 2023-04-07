package com.petrs.smartlab.ui.fragments.start.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petrs.smartlab.R
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.LoadingState
import com.petrs.smartlab.domain.useCases.ChangeOnboardingStatusUseCase
import com.petrs.smartlab.ui.fragments.start.onboarding.models.OnboardingData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val changeOnboardingStatusUseCase: ChangeOnboardingStatusUseCase
) : ViewModel() {

    private val _isEnd = MutableSharedFlow<Boolean>()
    val isEnd = _isEnd.asSharedFlow()

    private val _onboardingStatus: MutableStateFlow<DomainResult<Unit>> = MutableStateFlow(DomainResult.Loading(LoadingState.INITIAL))
    val onboardingStatus = _onboardingStatus.asStateFlow()

    fun getOnboardingData(): List<OnboardingData> {
        return listOf(
            OnboardingData(
                title = "Анализы",
                description = "Экспресс сбор и получение проб",
                image = R.drawable.ic_onboarding_image_1
            ),
            OnboardingData(
                title = "Уведомления",
                description = "Вы быстро узнаете о результатах",
                image = R.drawable.ic_onboarding_image_2
            ),
            OnboardingData(
                title = "Мониторинг",
                description = "Наши врачи всегда наблюдают \nза вашими показателями здоровья",
                image = R.drawable.ic_onboarding_image_3
            )
        )
    }

    fun changePage(pagePosition: Int) = viewModelScope.launch {
        when (pagePosition) {
            0, 1 -> {
                _isEnd.emit(false)
            }
            2 -> {
                _isEnd.emit(true)
            }
        }
    }

    fun changeOnboardingStatus() = viewModelScope.launch {
        _onboardingStatus.value = changeOnboardingStatusUseCase(true)
    }
}