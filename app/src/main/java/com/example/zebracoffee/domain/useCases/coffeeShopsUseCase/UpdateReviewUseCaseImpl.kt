package com.example.zebracoffee.domain.useCases.coffeeShopsUseCase

import com.example.zebracoffee.data.modelDto.ReviewListItem
import com.example.zebracoffee.domain.repository.CoffeeShopsRepository
import retrofit2.Response
import javax.inject.Inject

class UpdateReviewUseCaseImpl @Inject constructor(
    private val repository: CoffeeShopsRepository
): UpdateReviewUseCase {
    override suspend fun updateReview(
        bearerToken: String,
        grade: Int,
        text: String,
        shopId: Int,
        reviewId: Int,
    ): Response<ReviewListItem> {
        return repository.updateReview(bearerToken, grade, text, shopId, reviewId)
    }
}