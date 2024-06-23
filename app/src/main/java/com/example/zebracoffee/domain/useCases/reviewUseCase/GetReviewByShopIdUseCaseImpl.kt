package com.example.zebracoffee.domain.useCases.reviewUseCase

import com.example.zebracoffee.data.modelDto.ReviewList
import com.example.zebracoffee.domain.repository.CoffeeShopsRepository
import javax.inject.Inject

class GetReviewByShopIdUseCaseImpl @Inject constructor(
    private val repository: CoffeeShopsRepository
): GetReviewByShopIdUseCase {
    override suspend fun getReviewByShopId(shopId: Int, bearerToken: String): ReviewList {
        return repository.getReviewByShopId(shopId, bearerToken)
    }
}