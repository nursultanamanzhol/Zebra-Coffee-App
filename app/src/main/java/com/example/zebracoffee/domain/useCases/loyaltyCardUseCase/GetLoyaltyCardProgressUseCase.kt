package com.example.zebracoffee.domain.useCases.loyaltyCardUseCase

import com.example.zebracoffee.data.modelDto.LoyalCardProgressDto

interface GetLoyaltyCardProgressUseCase {
    suspend fun getLoyaltyCardProgress(bearerToken: String): LoyalCardProgressDto
}