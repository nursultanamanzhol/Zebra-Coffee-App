package com.example.zebracoffee.presentation.profileScreen

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zebracoffee.data.modelDto.DeleteAccountState
import com.example.zebracoffee.data.modelDto.LogoutResponse
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.di.HiltApplication
import com.example.zebracoffee.domain.useCases.localUseCase.DeleteUserUseCase
import com.example.zebracoffee.domain.useCases.localUseCase.LocalUserInfoUseCase
import com.example.zebracoffee.domain.useCases.profileuseCase.DeleteAccountUseCase
import com.example.zebracoffee.domain.useCases.profileuseCase.LogoutUseCase
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
class ProfileScreenViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val _isLogoutDialogVisible = MutableStateFlow(false)
    val isLogoutDialogVisible = _isLogoutDialogVisible.asStateFlow()

    private val _isDeleteDialogVisible = MutableStateFlow(false)
    val isDeleteDialogVisible = _isDeleteDialogVisible.asStateFlow()

    private val _logout = MutableStateFlow<Resource<LogoutResponse>>(Resource.Unspecified)
    val logout = _logout.asStateFlow()

    private val _deleteAccountState =
        MutableStateFlow<DeleteAccountState>(DeleteAccountState.UnSpecified)
    val deleteAccountState = _deleteAccountState.asStateFlow()

    private val bearerToken = sharedPreferences.getString("access_token", null)

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _userImage = MutableStateFlow("")
    val userImage: StateFlow<String> = _userImage

    private val _userPhone = MutableStateFlow("")
    val userPhone: StateFlow<String> = _userPhone

    private val _userBirthdate = MutableStateFlow("")
    val userBirthdate: StateFlow<String> = _userBirthdate

    private val _userPk = MutableStateFlow(-1)
    val userPk: StateFlow<Int> = _userPk

    private val userNameFromSharedPreferences = sharedPreferences.getString("name", null)
    private val userImageFromSharedPreferences = sharedPreferences.getString("avatar_image", null)
    private val userPhoneFromSharedPreferences = sharedPreferences.getString("phone", null)
    private val userBirthdateFromSharedPreferences = sharedPreferences.getString("birth_date", null)
    private val userPkShared = sharedPreferences.getInt("pk", -1)

    init {
        initializeUserInfo()
    }

    private fun initializeUserInfo(){
        _userName.value = userNameFromSharedPreferences ?: ""
        _userImage.value = userImageFromSharedPreferences ?: ""
        _userPhone.value = userPhoneFromSharedPreferences ?: ""
        _userBirthdate.value = userBirthdateFromSharedPreferences ?: ""
        _userPk.value = userPkShared
    }

    val theme: StateFlow<AppTheme> = HiltApplication.prefs.theme.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AppTheme.AUTO,
    )

    fun changeTheme(theme: AppTheme) {
        viewModelScope.launch { HiltApplication.prefs.changeTheme(theme) }
    }

    fun deleteUserAccount() {
        viewModelScope.launch {
            _deleteAccountState.value = DeleteAccountState.Loading
            try {
                deleteAccountUseCase.deleteUserAccount(bearerToken!!)
                _deleteAccountState.value = DeleteAccountState.Success
            } catch (e: Exception) {
                _deleteAccountState.value = DeleteAccountState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            _logout.value = Resource.Loading
            try {
                val result =
                    logoutUseCase.logout(bearerToken!!)
                if (result.isSuccessful) {
                    _logout.value = Resource.Success(result.body())
                } else {
                    _logout.value = Resource.Failure(result.message())
                }
            } catch (e: Exception) {
                _logout.value = Resource.Failure(e.message.toString())
            }
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            try {
                deleteUserUseCase.deleteUser(userId)
            } catch (e: Exception) {
                Log.d("ProfileScreenViewModel", e.message.toString())
            }
        }
    }

    fun clearTokens() {
        val editor = sharedPreferences.edit()
        editor.remove("access_token")
        editor.remove("refresh_token")
        editor.remove("saved_city_coffeeShop")
        editor.apply()
    }


    fun changeVisibilityLogoutDialog(value: Boolean) {
        viewModelScope.launch {
            _isLogoutDialogVisible.emit(value)
        }
    }

    fun changeVisibilityDeleteDialog(value: Boolean) {
        viewModelScope.launch {
            _isDeleteDialogVisible.emit(value)
        }
    }

    fun changeState(){
        _deleteAccountState.value = DeleteAccountState.Success
    }
}