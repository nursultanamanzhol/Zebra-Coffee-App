package com.example.zebracoffee.domain.useCases.profileuseCase

import com.example.zebracoffee.data.modelDto.LogoutResponse
import retrofit2.Response

interface LogoutUseCase {
    suspend fun logout(bearerToken: String): Response<LogoutResponse>
}