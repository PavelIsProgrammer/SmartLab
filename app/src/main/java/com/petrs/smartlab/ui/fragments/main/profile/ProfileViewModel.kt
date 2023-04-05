package com.petrs.smartlab.ui.fragments.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petrs.smartlab.data.models.CreateProfileBody
import com.petrs.smartlab.data.models.UpdateProfileBody
import com.petrs.smartlab.domain.models.ProfileInfoDomain
import com.petrs.smartlab.domain.useCases.GetProfileUseCase
import com.petrs.smartlab.domain.useCases.SaveProfileUseCase
import com.petrs.smartlab.domain.useCases.UpdateProfileUseCase
import com.petrs.smartlab.utils.network.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val saveProfileUseCase: SaveProfileUseCase
) : ViewModel() {

    private val _profile: MutableStateFlow<State<List<ProfileInfoDomain>>> = MutableStateFlow(State.Loading)
    val profile = _profile.asStateFlow()

    fun getProfile() {
        _profile.value = getProfileUseCase()
    }

    fun updateProfile(name: String, lastName: String, midName: String, birthDate: String, sexOrientation: String) = viewModelScope.launch {
        val state = _profile.value
        if (state is State.Success) {
            val newList = state.data
            val newProfile = updateProfileUseCase(
                UpdateProfileBody(
                    id = state.data[0].id,
                    firstName = name,
                    lastName = lastName,
                    midName = midName,
                    birth = birthDate,
                    sexOrientation = sexOrientation
                )
            )
            newList.map { if (it.id == state.data[0].id) newProfile else it }
            _profile.value = State.Success(newList)
        }
    }

    fun saveProfile(profile: List<ProfileInfoDomain>) = viewModelScope.launch {
        saveProfileUseCase(profile)
    }
}