package com.example.zebracoffee.presentation.registration.enterCodeScreen

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.data.modelDto.VerificationCodeResponse
import com.example.zebracoffee.domain.useCases.registrationUseCase.VerificationCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterCodeViewModel @Inject constructor(
    private val verifyCodeUseCase: VerificationCodeUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _verificationCode =
        MutableStateFlow<Resource<VerificationCodeResponse>>(Resource.Unspecified)
    val verificationCodeState = _verificationCode.asStateFlow()

    fun verifyCode(phone_number: String, sms_code: Int) {
        viewModelScope.launch(Dispatchers.IO){
            _verificationCode.value = Resource.Loading
            try {
                val result = verifyCodeUseCase.verifyCode(phone_number, sms_code)
                if (result.isSuccessful) {
                    val accessToken = result.body()?.accessToken
                    val refreshToken = result.body()?.refreshToken
                    if (refreshToken != null) {
                        if (accessToken != null) {
                            saveRefreshToken(accessToken, refreshToken)
                            Log.d("RefreshToken", "RefreshToken: $refreshToken")
                            Log.d("RefreshToken", "RefreshToken: $accessToken")
                        }
                    }
                    _verificationCode.value = Resource.Success(result.body())
                } else {
                    _verificationCode.value = Resource.Failure(result.message())
                }
            } catch (e: Exception) {
                _verificationCode.value = Resource.Failure(e.message.toString())
            }
        }
    }
    private fun saveRefreshToken(access_token: String, refreshToken: String) {
        val editor = sharedPreferences.edit()
        editor.putString("access_token", access_token)
        editor.putString("refresh_token", refreshToken)
        editor.apply()
    }

    fun resetState() {
        _verificationCode.value = Resource.Unspecified
    }
}

