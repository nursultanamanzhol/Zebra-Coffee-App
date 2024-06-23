package com.example.zebracoffee.data.network.remote.repositoryImpl

import com.example.zebracoffee.data.modelDto.LoyalCardProgressDto
import com.example.zebracoffee.data.network.remote.api.ZebraCoffeeApi
import com.example.zebracoffee.domain.repository.LoyaltyCardRepository
import javax.inject.Inject

class LoyaltyCardRepositoryImpl @Inject constructor(
    private val api: ZebraCoffeeApi
) : LoyaltyCardRepository {

    override suspend fun getLoyaltyCardProgress(bearerToken: String): LoyalCardProgressDto {
        val token = "Bearer $bearerToken"
        return api.getLoyaltyCardProgress(token)
    }
}