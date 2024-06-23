package com.example.zebracoffee.presentation.profileScreen.userInfoScreen

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.data.modelDto.UpdatedUserInfo
import com.example.zebracoffee.di.HiltApplication
import com.example.zebracoffee.domain.entity.AvatarImage
import com.example.zebracoffee.domain.useCases.homeUseCase.UpdateUserDataUseCase
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetAvatarImagesUseCase
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoScreenViewModel @Inject constructor(
    private val avatarImageUseCase: GetAvatarImagesUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    val sharedPreferences: SharedPreferences,
) : ViewModel() {

    val theme: StateFlow<AppTheme> = HiltApplication.prefs.theme.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AppTheme.AUTO,
    )

    private val _avatarImages = MutableStateFlow<List<AvatarImage>>(emptyList())
    val avatarImagesState = _avatarImages.asStateFlow()

    private val _updateUserInfo =
        MutableStateFlow<Resource<UpdatedUserInfo>>(Resource.Unspecified)
    val updateUserInfo = _updateUserInfo.asStateFlow()

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _userImage = MutableStateFlow("")
    val userImage: StateFlow<String> = _userImage

    private val _userPhone = MutableStateFlow("")
    val userPhone: StateFlow<String> = _userPhone

    private val _userBirthdate = MutableStateFlow("")
    val userBirthdate: StateFlow<String> = _userBirthdate

    private val bearerToken = sharedPreferences.getString("access_token", null)
    private val userNameFromSharedPreferences = sharedPreferences.getString("name", null)
    private val userImageFromSharedPreferences = sharedPreferences.getString("avatar_image", null)
    private val userPhoneFromSharedPreferences = sharedPreferences.getString("phone", null)
    private val userBirthdateFromSharedPreferences = sharedPreferences.getString("birth_date", null)

    init {
        getAvatarImages()
        initializeUserInfo()
    }

    private fun initializeUserInfo() {
        _userName.value = userNameFromSharedPreferences ?: ""
        _userImage.value = userImageFromSharedPreferences ?: ""
        _userPhone.value = userPhoneFromSharedPreferences ?: ""
        _userBirthdate.value = userBirthdateFromSharedPreferences ?: ""
    }

    fun setName(name: String) {
        _userName.update { name }
    }

    fun setBirthDate(birthDate: String) {
        _userBirthdate.update { birthDate }
    }

    fun setPhone(phone: String) {
        _userPhone.update { phone }
    }

    private fun getAvatarImages() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val avatarImages = avatarImageUseCase.getAvatarImages(bearerToken!!)
                _avatarImages.value = avatarImages
            } catch (e: Exception) {
                Log.d("UserInfoScreenViewModel", "error avatar images: ${e.message.toString()}")
            }
        }
    }

    fun updateUserInfo(name: String, phone: String, birthDate: String, avatarId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _updateUserInfo.value = Resource.Loading
            try {
                val result = updateUserDataUseCase.updateUserData(
                    name, phone, birthDate, avatarId, bearerToken!!
                )
                if (result.isSuccessful) {
                    _updateUserInfo.value = Resource.Success(result.body())
                    val userName = result.body()!!.name
                    val userImage = result.body()!!.avatar_image
                    val birthday = result.body()!!.birth_date
                    updateLocalUserData(userName, userImage, birthday)
                } else {
                    _updateUserInfo.value = Resource.Failure(result.message())
                }
            } catch (e: Exception) {
                _updateUserInfo.value = Resource.Failure(e.message.toString())
            }
        }
    }

    private fun updateLocalUserData(
        full_name: String,
        avatarImage: String,
        birthday: String,
    ) {
        val editor = sharedPreferences.edit()
        editor.putString("name", full_name)
        editor.putString("avatar_image", avatarImage)
        editor.putString("birth_date", birthday)
        editor.apply()
    }
    fun resetState() {
        _updateUserInfo.value = Resource.Unspecified
    }
}