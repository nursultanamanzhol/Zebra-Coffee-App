package com.example.zebracoffee.domain.useCases.registrationUseCase

import com.example.zebracoffee.data.modelDto.LoyaltyCardResponseDto
import retrofit2.Response

interface GetLoyaltyCardInfoUseCase {

    suspend fun getLoyaltyCardInfo(bearerToken: String): Response<LoyaltyCardResponseDto>

}