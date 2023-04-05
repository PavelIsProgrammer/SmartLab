package com.petrs.smartlab.utils.network

sealed class State<out T: Any> {
    class Success<out T: Any>(val data: T): State<T>()
    object Loading : State<Nothing>()
    class Error(val errorStringResourceId: Int): State<Nothing>()
}