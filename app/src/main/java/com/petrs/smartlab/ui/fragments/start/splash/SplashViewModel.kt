package com.petrs.smartlab.ui.fragments.start.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.LoadingState
import com.petrs.smartlab.domain.useCases.GetAppPasswordUseCase
import com.petrs.smartlab.domain.useCases.GetOnboardingStatusUseCase
import com.petrs.smartlab.domain.useCases.GetTokenUseCase
import com.petrs.smartlab.utils.network.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getAppPasswordUseCase: GetAppPasswordUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val getOnboardingStatusUseCase: GetOnboardingStatusUseCase
) : ViewModel() {

    private val _timer = MutableSharedFlow<Boolean>()
    val timer = _timer.asSharedFlow()

    private val _token: MutableStateFlow<State<String>> = MutableStateFlow(State.Loading)
    val token = _token.asStateFlow()

    private val _password: MutableStateFlow<State<String>> = MutableStateFlow(State.Loading)
    val password = _password.asStateFlow()

    private val _onboardingStatus: MutableStateFlow<DomainResult<Boolean>> = MutableStateFlow(DomainResult.Loading(LoadingState.INITIAL))
    val onboardingStatus = _onboardingStatus.asStateFlow()


    fun startTimer() = viewModelScope.launch {
        _timer.emit(false)
        delay(2000)
        _timer.emit(true)
    }

    fun checkToken() {
        _token.value = getTokenUseCase()
    }

    fun checkPassword() {
        _password.value = getAppPasswordUseCase()
    }

    fun checkOnboardingStatus() {
        _onboardingStatus.value = getOnboardingStatusUseCase()
    }
}