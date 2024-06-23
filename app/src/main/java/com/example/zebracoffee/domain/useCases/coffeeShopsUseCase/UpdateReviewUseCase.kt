package com.example.zebracoffee.domain.useCases.coffeeShopsUseCase

import com.example.zebracoffee.data.modelDto.ReviewListItem
import retrofit2.Response

interface UpdateReviewUseCase {
    suspend fun updateReview(
        bearerToken: String,
        grade: Int,
        text: String,
        shopId: Int,
        reviewId: Int
    ): Response<ReviewListItem>
}