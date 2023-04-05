package com.petrs.smartlab.ui.fragments.start.sign_in

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.LoadingState
import com.petrs.smartlab.domain.models.MessageDomain
import com.petrs.smartlab.domain.useCases.SendCodeUseCase
import com.petrs.smartlab.utils.network.State
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val sendCode: SendCodeUseCase
) : ViewModel() {

    private val _emailIsValid = MutableSharedFlow<Boolean>()
    val emailIsValid = _emailIsValid.asSharedFlow()

    private val _sendCodeToEmail: MutableStateFlow<DomainResult<MessageDomain>> = MutableStateFlow(DomainResult.Loading(LoadingState.INITIAL))
    val sendCodeToEmail = _sendCodeToEmail.asStateFlow()

    fun sendNewCode(email: String) = viewModelScope.launch {
        _sendCodeToEmail.value = DomainResult.Loading(LoadingState.REQUEST_LOADING)
        _sendCodeToEmail.value = sendCode(email)
    }

    fun validateEmail(email: String) = viewModelScope.launch {
        _emailIsValid.emit(checkEmail(email))
    }

    private fun checkEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}