package com.example.zebracoffee.domain.useCases.loyaltyCardUseCase

import com.example.zebracoffee.data.modelDto.FCMRegistrationToken
import com.example.zebracoffee.data.modelDto.FCMRegistrationTokenDto
import retrofit2.Response

interface FCMRegistrationTokenUseCase {
    suspend fun setFCMRegistrationToken(body: FCMRegistrationTokenDto, bearerToken: String): Response<Unit>
}