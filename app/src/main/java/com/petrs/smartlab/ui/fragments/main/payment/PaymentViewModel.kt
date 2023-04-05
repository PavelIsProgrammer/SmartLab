package com.petrs.smartlab.ui.fragments.main.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PaymentViewModel : ViewModel() {

    private val _status: MutableSharedFlow<Int> = MutableSharedFlow()
    val status = _status.asSharedFlow()

    fun start(num: Int) = viewModelScope.launch {
        _status.emit(num)
    }
}