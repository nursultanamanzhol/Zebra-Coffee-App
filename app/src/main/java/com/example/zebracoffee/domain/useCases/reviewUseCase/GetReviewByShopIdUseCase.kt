package com.example.zebracoffee.domain.useCases.reviewUseCase

import com.example.zebracoffee.data.modelDto.ReviewList

interface GetReviewByShopIdUseCase {
    suspend fun getReviewByShopId(shopId:Int,bearerToken: String): ReviewList
}