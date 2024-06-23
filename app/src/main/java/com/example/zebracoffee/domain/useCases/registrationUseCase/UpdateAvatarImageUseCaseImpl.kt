package com.example.zebracoffee.domain.useCases.registrationUseCase

import com.example.zebracoffee.data.modelDto.PhoneNumberResponse
import com.example.zebracoffee.domain.repository.RegistrationRepository
import retrofit2.Response
import javax.inject.Inject

class UpdateAvatarImageUseCaseImpl @Inject constructor(
    private val repository: RegistrationRepository
) : UpdateAvatarImageUseCase {
    override suspend fun updateAvatarImage(
        avatar_choice: Int,
        bearerToken: String
    ): Response<PhoneNumberResponse> {
        return repository.updateAvatarImage(avatar_choice, bearerToken)
    }
}