package com.example.zebracoffee.domain.useCases.registrationUseCase

import com.example.zebracoffee.data.modelDto.UserDto
import com.example.zebracoffee.domain.repository.RegistrationRepository
import retrofit2.Response
import javax.inject.Inject

class GetUserDataUseCaseImpl @Inject constructor(
    private val repository: RegistrationRepository
): GetUserDataUseCase {
    override suspend fun getUserData(bearerToken: String): Response<UserDto> {
        return repository.getUserData(bearerToken)
    }
}