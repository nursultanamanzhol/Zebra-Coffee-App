package com.example.zebracoffee.data.modelDto

import com.google.gson.annotations.SerializedName

data class BottomLoyaltyDto(
    @SerializedName("id") val id: String,
    val discount_percentage: Int,
    val image: String,
    val is_current: Boolean,
    val orders_required: Int
)