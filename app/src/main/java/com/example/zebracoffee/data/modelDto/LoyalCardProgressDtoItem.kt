package com.example.zebracoffee.data.modelDto

import com.example.zebracoffee.domain.entity.LoyaltyCardProgress
import com.google.gson.annotations.SerializedName

data class LoyaltyCardProgressDtoItem(
    override val id: Int,
    override val name: String,
    @SerializedName("discount_percentage") override val discountPercentage: Int,
    override val threshold: Int,
    @SerializedName("progress_percentage") override val progressPercentage: Int,
    @SerializedName("is_current") override val isCurrent: Boolean,
    override val image: String
): LoyaltyCardProgress
