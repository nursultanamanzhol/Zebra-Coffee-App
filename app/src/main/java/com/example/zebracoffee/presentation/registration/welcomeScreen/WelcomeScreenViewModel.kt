package com.example.zebracoffee.presentation.registration.welcomeScreen

import android.content.SharedPreferences
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zebracoffee.data.modelDto.LoyaltyCardResponseDto
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.data.modelDto.UserDto
import com.example.zebracoffee.di.HiltApplication
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
class WelcomeScreenViewModel @Inject constructor(
    private val loyaltyCardInfoUseCase: GetLoyaltyCardInfoUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    val sharedPreferences: SharedPreferences,
) : ViewModel() {

    val theme: StateFlow<AppTheme> = HiltApplication.prefs.theme.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AppTheme.AUTO,
    )
    private val _loyaltyCard =
        MutableStateFlow<Resource<LoyaltyCardResponseDto>>(Resource.Unspecified)
    val loyaltyCard = _loyaltyCard.asStateFlow()

    private val _userDataCard = MutableStateFlow<Resource<UserDto>>(Resource.Unspecified)
    val userDataCard = _userDataCard.asStateFlow()

    val uiState = MutableStateFlow<WelcomeScreenState>(WelcomeScreenState.Loading)

    init {
        getLoyaltyCardInfo()
        getUserData()
    }

    private fun getLoyaltyCardInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _loyaltyCard.value = Resource.Loading
            try {
                val bearerToken = sharedPreferences.getString("access_token", null)
                val result =
                    loyaltyCardInfoUseCase.getLoyaltyCardInfo(bearerToken!!)
                if (result.isSuccessful) {
                    uiState.value = WelcomeScreenState.Success(
                        a = 2
                    )
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
            _userDataCard.value = Resource.Loading
            try {
                val bearerToken = sharedPreferences.getString("access_token", null)
                val result =
                    getUserDataUseCase.getUserData(bearerToken!!)
                if (result.isSuccessful) {
                    _userDataCard.value = Resource.Success(result.body())
                    val userName = result.body()!!.name
                    val userImage = result.body()!!.avatar_image
                    val phoneNumber = result.body()!!.phone
                    val userBirthDate = result.body()!!.birth_date
                    val primaryKey = result.body()!!.pk
                    saveLocalUserData(userName, userImage, phoneNumber, userBirthDate,
                        primaryKey
                    )
                } else {
                    _userDataCard.value = Resource.Failure(result.message())
                }
            } catch (e: Exception) {
                _userDataCard.value = Resource.Failure(e.message.toString())
            }
        }
    }

    private fun saveLocalUserData(
        name: String, avatarImage: String, phone: String, userBirthDate: String,pk:Int
    ) {
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("avatar_image", avatarImage)
        editor.putString("phone", phone)
        editor.putString("birth_date", userBirthDate)
        editor.putInt("pk",pk)
        editor.apply()
    }

    fun resetState() {
        _loyaltyCard.value = Resource.Unspecified
        _userDataCard.value = Resource.Unspecified
    }

}

@Stable
sealed interface WelcomeScreenState {
    object Loading: WelcomeScreenState
    data class Success(
        val a: Int
    ): WelcomeScreenState
}