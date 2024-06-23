package com.example.zebracoffee.presentation.registration.chooseAvatar

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zebracoffee.data.modelDto.PhoneNumberResponse
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.domain.entity.AvatarImage
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetAvatarImagesUseCase
import com.example.zebracoffee.domain.useCases.registrationUseCase.UpdateAvatarImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvatarViewModel @Inject constructor(
    private val avatarImagesUseCase: GetAvatarImagesUseCase,
    private val updateAvatarImageUseCase: UpdateAvatarImageUseCase,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _avatarImages = MutableStateFlow<List<AvatarImage>>(emptyList())
    val avatarImagesState = _avatarImages.asStateFlow()

    private val _updateAvatar =
        MutableStateFlow<Resource<PhoneNumberResponse>>(Resource.Unspecified)
    val updateAvatar = _updateAvatar.asStateFlow()

    val bearerToken = sharedPreferences.getString("access_token", null)


    init {
        getAvatarImages()
    }
    private fun getAvatarImages() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val avatarImages = avatarImagesUseCase.getAvatarImages(bearerToken!!)
                _avatarImages.value = avatarImages
            }
            catch (_: Exception) { }
        }
    }

    fun updateAvatarImage(avatar_choice: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _updateAvatar.value = Resource.Loading
            try {
                val result = updateAvatarImageUseCase.updateAvatarImage(
                    avatar_choice,
                    bearerToken!!
                )
                if (result.isSuccessful) {
                    _updateAvatar.value = Resource.Success(result.body())
                } else {
                    _updateAvatar.value = Resource.Failure(result.message())
                }
            } catch (e: Exception) {
                _updateAvatar.value = Resource.Failure(e.message.toString())
            }
        }
    }

    fun resetState(){
        _updateAvatar.value = Resource.Unspecified
    }
}