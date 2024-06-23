package com.example.zebracoffee.domain.useCases.registrationUseCase

import com.example.zebracoffee.data.modelDto.PhoneNumberResponse
import retrofit2.Response

interface UpdateAvatarImageUseCase {

    suspend fun updateAvatarImage(
        avatar_choice: Int,
        bearerToken: String
    ): Response<PhoneNumberResponse>
}