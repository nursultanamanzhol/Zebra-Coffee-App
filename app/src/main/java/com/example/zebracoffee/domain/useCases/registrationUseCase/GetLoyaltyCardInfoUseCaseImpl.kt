package com.example.zebracoffee.domain.useCases.registrationUseCase

import com.example.zebracoffee.data.modelDto.LoyaltyCardResponseDto
import com.example.zebracoffee.domain.repository.RegistrationRepository
import retrofit2.Response
import javax.inject.Inject

class GetLoyaltyCardInfoUseCaseImpl @Inject constructor(
    private val repository: RegistrationRepository
): GetLoyaltyCardInfoUseCase {
    override suspend fun getLoyaltyCardInfo(bearerToken: String): Response<LoyaltyCardResponseDto> {
        return repository.getLoyaltyCardInfo(bearerToken)
    }
}