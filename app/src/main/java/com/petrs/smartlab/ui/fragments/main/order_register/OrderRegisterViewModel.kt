package com.petrs.smartlab.ui.fragments.main.order_register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petrs.smartlab.data.models.CatalogItemRequestBody
import com.petrs.smartlab.data.models.CreateOrderRequestBody
import com.petrs.smartlab.data.models.CreateProfileBody
import com.petrs.smartlab.data.models.PatientRequestBody
import com.petrs.smartlab.domain.DomainResult
import com.petrs.smartlab.domain.LoadingState
import com.petrs.smartlab.domain.models.CatalogItemDomain
import com.petrs.smartlab.domain.models.CreateOrderDomain
import com.petrs.smartlab.domain.models.ProfileInfoDomain
import com.petrs.smartlab.domain.useCases.*
import com.petrs.smartlab.ui.fragments.main.order_register.models.CartItem
import com.petrs.smartlab.ui.fragments.main.order_register.models.PatientItem
import com.petrs.smartlab.utils.network.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderRegisterViewModel(
    private val getCartUseCase: GetCartUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    private val createProfileUseCase: CreateProfileUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val saveCartUseCase: SaveCartUseCase
) : ViewModel() {

    private val _cart: MutableStateFlow<State<List<CatalogItemDomain>>> =
        MutableStateFlow(State.Loading)
    val cart = _cart.asStateFlow()

    private val _result: MutableStateFlow<State<List<Pair<PatientItem, List<CartItem>>>>> =
        MutableStateFlow(State.Loading)
    val result = _result.asStateFlow()

    private val _patients: MutableStateFlow<State<List<ProfileInfoDomain>>> =
        MutableStateFlow(State.Loading)
    val patients = _patients.asStateFlow()

    private val _profileInfo: MutableStateFlow<DomainResult<ProfileInfoDomain>> =
        MutableStateFlow(DomainResult.Loading(LoadingState.INITIAL))
    val profileInfo = _profileInfo.asStateFlow()

    private val _saveProfile: MutableStateFlow<State<Unit>> = MutableStateFlow(State.Loading)
    val saveProfile = _saveProfile.asStateFlow()

    private val _createOrder: MutableStateFlow<DomainResult<CreateOrderDomain>> = MutableStateFlow(DomainResult.Loading(LoadingState.INITIAL))
    val createOrder = _createOrder.asStateFlow()

    var staticCart = listOf<CatalogItemDomain>()
    var staticPatients = listOf<ProfileInfoDomain>()
    var staticResult = listOf<Pair<PatientItem, List<CartItem>>>()

    val orderRequestBody = CreateOrderRequestBody("", "", "", patients = emptyList())

    fun getCart() = viewModelScope.launch {
        _cart.value = getCartUseCase()
    }

    fun submitNewAnalyzes(list: List<Pair<PatientItem, List<CartItem>>>, onComplete: () -> Unit) = viewModelScope.launch {
        _result.value = State.Loading
        staticResult = list
        _result.value = State.Success(list)

        val newPatientsRequestList = arrayListOf<PatientRequestBody>()
        staticResult.forEach {
            if (it.first.selected) {
                val newCatalogItemRequestBodyList = arrayListOf<CatalogItemRequestBody>()
                it.second.forEach { cartItem ->
                    if (cartItem.selected) newCatalogItemRequestBodyList.add(
                        CatalogItemRequestBody(
                            id = cartItem.catalogItem.id,
                            price = cartItem.catalogItem.price
                        )
                    )
                }
                newPatientsRequestList.add(
                    PatientRequestBody(
                        name = it.first.profile.lastName + " " + it.first.profile.firstName,
                        items = newCatalogItemRequestBodyList
                    )
                )
            }
        }

        orderRequestBody.patients = newPatientsRequestList
    }.invokeOnCompletion { onComplete() }

    fun getPatients() = viewModelScope.launch {
        _patients.value = getProfileUseCase()
    }

    fun createProfile(
        name: String,
        lastName: String,
        midName: String,
        birthDate: String,
        sexOrientation: String
    ) = viewModelScope.launch {
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
        val state = patients.value
        if (state is State.Success) {
            _saveProfile.value = saveProfileUseCase(state.data + profile)
            getPatients()
        }
    }

    fun createOrder() = viewModelScope.launch {
        _createOrder.value = DomainResult.Loading(LoadingState.REQUEST_LOADING)
        _createOrder.value = createOrderUseCase(orderRequestBody)
        saveCartUseCase(emptyList())
    }
}