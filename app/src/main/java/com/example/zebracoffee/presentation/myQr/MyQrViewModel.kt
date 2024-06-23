package com.example.zebracoffee.presentation.myQr

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zebracoffee.data.modelDto.QrResponseDto
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.domain.useCases.homeUseCase.GetUserQrUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyQrViewModel @Inject constructor(
    private val getUserQrUseCase: GetUserQrUseCase,
    sharedPreferences: SharedPreferences
): ViewModel(){

    private val _userQr = MutableStateFlow<Resource<QrResponseDto>>(Resource.Loading)
    val userQr = _userQr.asStateFlow()

    val bearerToken = sharedPreferences.getString("access_token", null)

    fun getUserQr(){
        viewModelScope.launch(Dispatchers.IO) {
            _userQr.value = Resource.Loading
            try {
                val result =
                    getUserQrUseCase.getUserQr(bearerToken!!)
                if (result.isSuccessful) {
                    _userQr.value = Resource.Success(result.body())
                } else {
                    _userQr.value = Resource.Failure(result.message())
                }
            } catch (e: Exception) {
                _userQr.value = Resource.Failure(e.message.toString())
            }
        }
    }
}
