package com.example.zebracoffee.data.modelDto

sealed class StateResult {
    data class Success<T>(val data: T?) : StateResult()
    data class Error(val error: String) : StateResult()
    object Loading : StateResult()
}