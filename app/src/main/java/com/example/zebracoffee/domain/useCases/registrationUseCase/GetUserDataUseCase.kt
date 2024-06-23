package com.example.zebracoffee.domain.useCases.registrationUseCase

import com.example.zebracoffee.data.modelDto.UserDto
import retrofit2.Response

interface GetUserDataUseCase {
    suspend fun getUserData(bearerToken: String): Response<UserDto>
}