package com.example.zebracoffee.data.modelDto

import com.example.zebracoffee.domain.entity.LoyaltyCardResponse

data class LoyaltyCardResponseDto(
    override val discount_percentage: Int,
    override val id: Int,
    override val image: String,
    override val name: String,
    override val progress_percentage: Int,
    override val threshold: Int
) : LoyaltyCardResponse
