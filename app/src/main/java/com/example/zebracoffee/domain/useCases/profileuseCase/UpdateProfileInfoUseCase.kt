package com.example.zebracoffee.domain.useCases.profileuseCase

import com.example.zebracoffee.data.modelDto.PhoneNumberResponse
import retrofit2.Response

interface UpdateProfileInfoUseCase {
    suspend fun updateInfoProfile(
        name: String,
        birth_date: String,
        token: String
    ): Response<PhoneNumberResponse>
}