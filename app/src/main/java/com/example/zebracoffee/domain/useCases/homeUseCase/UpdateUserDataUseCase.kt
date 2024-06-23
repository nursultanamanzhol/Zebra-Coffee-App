package com.example.zebracoffee.domain.useCases.homeUseCase

import com.example.zebracoffee.data.modelDto.UpdatedUserInfo
import retrofit2.Response

interface UpdateUserDataUseCase {
    suspend fun updateUserData(
        name: String,
        phone: String,
        birthDate: String,
        avatarId: Int,
        bearerToken: String,
    ): Response<UpdatedUserInfo>
}