package com.example.zebracoffee.presentation.registration.typeNumber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zebracoffee.data.modelDto.PhoneNumberResponse
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetPhoneNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TypeNumberViewModel @Inject constructor(
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase
) : ViewModel() {

    private val _phoneNumberState =
        MutableStateFlow<Resource<PhoneNumberResponse>>(Resource.Unspecified)
    val phoneNumberState = _phoneNumberState.asStateFlow()


    fun getPhoneNumber(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _phoneNumberState.value = Resource.Loading
            try {
                val result = getPhoneNumberUseCase.getPhoneNumber(phoneNumber)
                if (result.isSuccessful) {
                    _phoneNumberState.value = Resource.Success(result.body())
                } else {
                    _phoneNumberState.value = Resource.Failure(result.message())
                }
            } catch (e: Exception) {
                _phoneNumberState.value = Resource.Failure(e.message.toString())
            }
        }
    }

    fun resetState() {
        _phoneNumberState.value = Resource.Unspecified
    }
}