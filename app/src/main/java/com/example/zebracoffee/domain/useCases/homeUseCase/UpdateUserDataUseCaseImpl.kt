package com.example.zebracoffee.domain.useCases.homeUseCase

import com.example.zebracoffee.data.modelDto.UpdatedUserInfo
import com.example.zebracoffee.domain.repository.HomeRepository
import com.example.zebracoffee.domain.repository.RegistrationRepository
import retrofit2.Response
import javax.inject.Inject

class UpdateUserDataUseCaseImpl @Inject constructor(
    private val repository: RegistrationRepository
): UpdateUserDataUseCase {
    override suspend fun updateUserData(
        name: String,
        phone: String,
        birthDate: String,
        avatarId: Int,
        bearerToken: String,
    ): Response<UpdatedUserInfo> {
        return repository.updateUserData(name, phone, birthDate, avatarId, bearerToken)
    }
}