package com.example.zebracoffee.data.modelDto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CreateReviewBody(
    val grade: Int,
    val text: String,
    @SerializedName("shop_id") val shopId: Int,
)