package com.petrs.smartlab.ui.fragments.start.create_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petrs.smartlab.domain.models.ProfileInfoDomain
import com.petrs.smartlab.domain.useCases.GetAppPasswordUseCase
import com.petrs.smartlab.domain.useCases.GetProfileUseCase
import com.petrs.smartlab.domain.useCases.SaveAppPasswordUseCase
import com.petrs.smartlab.utils.network.State
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.security.PrivateKey

class CreatePasswordViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val savePasswordUseCase: SaveAppPasswordUseCase
) : ViewModel() {

    private val _password = MutableSharedFlow<String>()
    val password = _password.asSharedFlow()

    private val _profileIsCreated: MutableStateFlow<State<List<ProfileInfoDomain>>> = MutableStateFlow(State.Loading)
    val profileIsCreated = _profileIsCreated.asStateFlow()

    private var passwordStr = ""

    fun addNumberToPassword(num: Int) = viewModelScope.launch {
        passwordStr += num
        _password.emit(passwordStr)
    }

    fun deleteLast() = viewModelScope.launch {
        passwordStr = passwordStr.dropLast(1)
        _password.emit(passwordStr)
    }

    fun checkProfile() = viewModelScope.launch {
        savePasswordUseCase(passwordStr)
        _profileIsCreated.value = getProfileUseCase()
    }
}