package com.example.zebracoffee.presentation.registration.personalInfo

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zebracoffee.data.modelDto.PhoneNumberResponse
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.domain.useCases.profileuseCase.UpdateProfileInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    private val infoUseCase: UpdateProfileInfoUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _updateInfo = MutableStateFlow<Resource<PhoneNumberResponse>>(Resource.Unspecified)
    val updateInfoState = _updateInfo.asStateFlow()

    fun updateInfo(name: String, birthDate: String) {
        viewModelScope.launch(Dispatchers.IO){
            _updateInfo.value = Resource.Loading
            try {
                val bearerToken = sharedPreferences.getString("access_token", null)
                val result = infoUseCase.updateInfoProfile(
                    name,
                    birthDate,
                    bearerToken!!
                )
                if (result.isSuccessful) {
                    _updateInfo.value = Resource.Success(result.body())
                } else {
                    _updateInfo.value = Resource.Failure(result.message())
                }
            } catch (e: Exception) {
                _updateInfo.value = Resource.Failure(e.message.toString())
            }
        }
    }

    fun resetState() {
        _updateInfo.value = Resource.Unspecified
    }
}