package com.example.zebracoffee.data.modelDto

import com.google.gson.annotations.SerializedName

data class VerificationCodeResponse(
    @SerializedName("access_token") val accessToken: String,
    val message: String,
    @SerializedName("mobile_user_id") val mobileUserId: Int,
    @SerializedName("refresh_token") val refreshToken: String,
    val username: String,
)