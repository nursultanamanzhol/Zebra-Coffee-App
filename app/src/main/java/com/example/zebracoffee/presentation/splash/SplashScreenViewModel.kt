package com.example.zebracoffee.presentation.splash

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.data.modelDto.UserDto
import com.example.zebracoffee.domain.useCases.localUseCase.LocalUserInfoUseCase
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val localUserInfoUseCase: LocalUserInfoUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

//    fun refreshToken(): Boolean {
//        val refreshToken = sharedPreferences.getString("refresh_token", null)
//        if (!refreshToken.isNullOrEmpty()) {
//            Log.d("RefreshToken", refreshToken.toString())
//            api.refreshToken(/*"Bearer $refreshToken",*/ RefreshTokenBody(refreshToken)).enqueue(object :
//                Callback<RefreshTokenResponse> {
//                override fun onResponse(call: Call<RefreshTokenResponse>, response: Response<RefreshTokenResponse>) {
//                    if (response.isSuccessful) {
//                        val newAccessToken = response.body()?.access_token
//                        sharedPreferences.edit().putString("access_token", newAccessToken).apply()
//                        Log.d("RefreshTokenAccess", "$newAccessToken")
//
//                    } else {
//                        Log.e("SplashScreenViewModel", "Token refresh failed with response code: ${response.code()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable) {
//                    Log.e("SplashScreenViewModel", "Token refresh failed", t)
//                }
//            })
//            return true
//        } else {
//            Log.e("SplashScreenViewModel", "No refresh token available")
//            return false
//        }
//    }


    private val _userInfo = MutableStateFlow<List<UserDto>>(emptyList())
    val userInfo: StateFlow<List<UserDto>> get() = _userInfo

    private val _userData = MutableStateFlow<Resource<UserDto>>(Resource.Loading)
    val userData = _userData.asStateFlow()

    private val _userStatus = MutableStateFlow("")
    val userStatus = _userStatus.asStateFlow()

    val bearerToken = sharedPreferences.getString("access_token", null)

    init {
        getUserData()
    }

    fun checkUserToken(): Boolean {
        val token = sharedPreferences.getString("refresh_token", "")
        return !token.isNullOrBlank()
    }

    fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            _userData.value = Resource.Loading
            try {
                val result =
                    getUserDataUseCase.getUserData(bearerToken!!)
                if (result.isSuccessful) {
                    _userData.value = Resource.Success(result.body())
                } else {
                    _userData.value = Resource.Failure(result.message())
                }
            } catch (e: Exception) {
                _userData.value = Resource.Failure(e.message.toString())
            }
        }
    }

    /*private fun getUserInfo() {
        viewModelScope.launch {
            localUserInfoUseCase.getAllUser().collect { userInfoList ->
                _userInfo.update { userInfoList }
                Log.d("ProfileScreenViewModel", "getUserInfo: $userInfoList")
            }
        }
    }*/

    fun setStatus(status: String) {
        _userStatus.update { status }
    }

    /*
        private fun getUserInfoLocal() {
            viewModelScope.launch {
                localUserInfoUseCase.getAllUser().collect { userInfoList ->
                    val firstUser: UserDto? = userInfoList.firstOrNull()
                    firstUser?.let { setStatus(it.status) }
                    _userInfo.update { userInfoList }
                }
            }
        }
    */


}