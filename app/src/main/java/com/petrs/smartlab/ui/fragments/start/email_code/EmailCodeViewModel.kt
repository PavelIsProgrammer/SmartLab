package com.petrs.smartlab.ui.fragments.start.email_code

import android.icu.text.Transliterator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.LoadingState
import com.petrs.smartlab.domain.models.MessageDomain
import com.petrs.smartlab.domain.models.TokenDomain
import com.petrs.smartlab.domain.useCases.SaveTokenUseCase
import com.petrs.smartlab.domain.useCases.SendCodeUseCase
import com.petrs.smartlab.domain.useCases.SignInUseCase
import com.petrs.smartlab.utils.network.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmailCodeViewModel(
    private val sendCodeUseCase: SendCodeUseCase,
    private val signInUseCase: SignInUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {

    private val _timer = MutableSharedFlow<Int>()
    val timer = _timer.asSharedFlow()

    private val _sendCodeToEmail: MutableStateFlow<DomainResult<MessageDomain>> = MutableStateFlow(DomainResult.Loading(LoadingState.INITIAL))
    val sendCodeToEmail = _sendCodeToEmail.asStateFlow()

    private val _token: MutableStateFlow<DomainResult<TokenDomain>> = MutableStateFlow(DomainResult.Loading(LoadingState.INITIAL))
    val token = _token.asStateFlow()

    private val _saveToken: MutableStateFlow<State<Unit>> = MutableStateFlow(State.Loading)
    val saveToken = _saveToken.asStateFlow()

    fun startTimer() = viewModelScope.launch {
        _timer.emit(60)
        for (i in 59 downTo 0) {
            delay(1000)
            _timer.emit(i)
        }
    }

    fun sendNewCode(email: String) = viewModelScope.launch {
        _sendCodeToEmail.value = DomainResult.Loading(LoadingState.REQUEST_LOADING)
        _sendCodeToEmail.value = sendCodeUseCase(email)
    }

    fun signIn(email: String, code: String) = viewModelScope.launch {
        _token.value = DomainResult.Loading(LoadingState.REQUEST_LOADING)
        _token.value = signInUseCase(email, code)
    }

    fun saveToken(token: String) {
        _saveToken.value = saveTokenUseCase(token)
    }
}