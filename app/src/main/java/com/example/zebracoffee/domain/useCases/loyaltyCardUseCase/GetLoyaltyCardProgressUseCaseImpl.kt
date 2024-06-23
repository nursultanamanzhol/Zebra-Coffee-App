package com.example.zebracoffee.domain.useCases.loyaltyCardUseCase

import com.example.zebracoffee.data.modelDto.LoyalCardProgressDto
import com.example.zebracoffee.domain.repository.LoyaltyCardRepository
import javax.inject.Inject

class GetLoyaltyCardProgressUseCaseImpl @Inject constructor(
    private val repository: LoyaltyCardRepository
) : GetLoyaltyCardProgressUseCase {
    override suspend fun getLoyaltyCardProgress(bearerToken: String): LoyalCardProgressDto {
        return repository.getLoyaltyCardProgress(bearerToken)
    }
}