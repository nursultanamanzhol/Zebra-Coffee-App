package com.example.zebracoffee.domain.useCases.coffeeShopsUseCase

import com.example.zebracoffee.data.modelDto.ReviewListItem
import com.example.zebracoffee.domain.repository.CoffeeShopsRepository
import retrofit2.Response
import javax.inject.Inject

class CreateNewReviewUseCaseImpl @Inject constructor(
    private val repository: CoffeeShopsRepository
): CreateNewReviewUseCase {
    override suspend fun createNewRequest(
        bearerToken: String,
        grade: Int,
        text: String,
        shopId: Int,
    ): Response<ReviewListItem> {
        return repository.createNewRequest(bearerToken,grade, text, shopId)
    }
}