package com.example.zebracoffee.domain.useCases.coffeeShopsUseCase

import com.example.zebracoffee.data.modelDto.ReviewListItem
import retrofit2.Response

interface CreateNewReviewUseCase {
    suspend fun createNewRequest(
        bearerToken: String,
        grade: Int,
        text: String,
        shopId: Int,
    ): Response<ReviewListItem>
}