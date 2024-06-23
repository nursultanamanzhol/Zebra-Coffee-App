package com.example.zebracoffee.domain.useCases.profileuseCase

import com.example.zebracoffee.data.modelDto.LogoutResponse
import com.example.zebracoffee.domain.repository.ProfileRepository
import retrofit2.Response
import javax.inject.Inject

class LogoutUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
): LogoutUseCase {
    override suspend fun logout(bearerToken: String): Response<LogoutResponse> {
        return repository.logout(bearerToken)
    }
}