package com.example.zebracoffee.presentation.loyalProgram

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zebracoffee.data.modelDto.BottomLoyaltyDto
import com.example.zebracoffee.data.modelDto.LoyaltyCardResponseDto
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.data.modelDto.UserDto
import com.example.zebracoffee.di.HiltApplication
import com.example.zebracoffee.domain.entity.LoyaltyCardProgress
import com.example.zebracoffee.domain.useCases.loyaltyCardUseCase.GetLoyaltyCardProgressUseCase
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetLoyaltyCardInfoUseCase
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetUserDataUseCase
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoyalScreenViewModel @Inject constructor(
    private val getLoyaltyUseCase: GetLoyaltyCardProgressUseCase,
//    private val getLoyaltyCardUse: GetLoyaltyCardUseCase,
    private val sharedPreferences: SharedPreferences,

    private val loyaltyCardInfoUseCase: GetLoyaltyCardInfoUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val sharedPreferences1: SharedPreferences,
) : ViewModel() {

    private val _loyaltyProgress = MutableStateFlow<List<LoyaltyCardProgress>>(emptyList())
    val loyaltyProgressState = _loyaltyProgress.asStateFlow()

    private val _loyaltyBottom =
        MutableStateFlow<Resource<BottomLoyaltyDto>>(Resource.Loading)
    val loyaltyBottomState = _loyaltyBottom.asStateFlow()

    val theme: StateFlow<AppTheme> = HiltApplication.prefs.theme.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AppTheme.AUTO,
    )


    init {
        getLoyalProgressViewModel()
    }

    private fun getLoyalProgressViewModel() {
        viewModelScope.launch {
            try {
                val bearerToken = sharedPreferences.getString("access_token", null)
                val result = getLoyaltyUseCase.getLoyaltyCardProgress(bearerToken!!)
                _loyaltyProgress.value = result
            } catch (e: Exception) {
                Log.d("MyTAG", "Exception $e")
            }
        }
    }


    private val _loyaltyCard =
        MutableStateFlow<Resource<LoyaltyCardResponseDto>>(Resource.Unspecified)
    val loyaltyCard = _loyaltyCard.asStateFlow()

    private val _userDataCard = MutableStateFlow<Resource<UserDto>>(Resource.Unspecified)
    val userDataCard = _userDataCard.asStateFlow()

    init {
        getLoyaltyCardInfo()
        getUserData()
    }

    private fun getLoyaltyCardInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _loyaltyCard.value = Resource.Loading
            try {
                val bearerToken = sharedPreferences1.getString("access_token", null)
                val result =
                    loyaltyCardInfoUseCase.getLoyaltyCardInfo(bearerToken!!)
                if (result.isSuccessful) {
                    _loyaltyCard.value = Resource.Success(result.body())
                } else {
                    _loyaltyCard.value = Resource.Failure(result.message())
                }
            } catch (e: Exception) {
                _loyaltyCard.value = Resource.Failure(e.message.toString())
            }
        }
    }

    private fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loyaltyCard.value = Resource.Loading
            try {
                val bearerToken = sharedPreferences1.getString("access_token", null)
                val result =
                    getUserDataUseCase.getUserData(bearerToken!!)
                if (result.isSuccessful) {
                    _userDataCard.value = Resource.Success(result.body())
                } else {
                    _userDataCard.value = Resource.Failure(result.message())
                }
            } catch (e: Exception) {
                _userDataCard.value = Resource.Failure(e.message.toString())
            }
        }
    }

    fun resetState() {
        _loyaltyCard.value = Resource.Unspecified
        _userDataCard.value = Resource.Unspecified
    }
}