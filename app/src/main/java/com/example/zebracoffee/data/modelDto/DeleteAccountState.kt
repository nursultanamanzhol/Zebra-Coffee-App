package com.example.zebracoffee.data.modelDto

sealed class DeleteAccountState {
    object Loading : DeleteAccountState()
    object Success : DeleteAccountState()
    object UnSpecified : DeleteAccountState()
    data class Error(val message: String) : DeleteAccountState()
}