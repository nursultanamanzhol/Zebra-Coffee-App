package com.example.zebracoffee.domain.repository

import com.example.zebracoffee.data.modelDto.LoyalCardProgressDto

interface LoyaltyCardRepository {
    suspend fun getLoyaltyCardProgress(bearerToken: String): LoyalCardProgressDto
}