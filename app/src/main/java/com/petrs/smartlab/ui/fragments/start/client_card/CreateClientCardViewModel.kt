package com.petrs.smartlab.ui.fragments.start.client_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petrs.smartlab.data.models.CreateProfileBody
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.LoadingState
import com.petrs.smartlab.domain.models.ProfileInfoDomain
import com.petrs.smartlab.domain.useCases.CreateProfileUseCase
import com.petrs.smartlab.domain.useCases.SaveProfileUseCase
import com.petrs.smartlab.utils.network.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateClientCardViewModel(
    private val createProfileUseCase: CreateProfileUseCase,
    private val saveProfileUseCase: SaveProfileUseCase
) : ViewModel() {

    private val _profileInfo: MutableStateFlow<DomainResult<ProfileInfoDomain>> =
        MutableStateFlow(DomainResult.Loading(LoadingState.INITIAL))
    val profileInfo = _profileInfo.asStateFlow()

    private val _saveProfile: MutableStateFlow<State<Unit>> = MutableStateFlow(State.Loading)
    val saveProfile = _saveProfile.asStateFlow()

    fun createProfile(name: String, lastName: String, midName: String, birthDate: String, sexOrientation: String) = viewModelScope.launch {
        _profileInfo.value = DomainResult.Loading(LoadingState.REQUEST_LOADING)
        _profileInfo.value = createProfileUseCase(
            CreateProfileBody(
                firstName = name,
                lastName = lastName,
                midName = midName,
                birth = birthDate,
                sexOrientation = sexOrientation
            )
        )
    }

    fun saveProfile(profile: ProfileInfoDomain) = viewModelScope.launch {
        _saveProfile.value = saveProfileUseCase(listOf(profile))
    }
}