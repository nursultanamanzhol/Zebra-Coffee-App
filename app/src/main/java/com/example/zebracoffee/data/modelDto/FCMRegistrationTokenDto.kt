package com.example.zebracoffee.data.modelDto

import com.google.gson.annotations.SerializedName

data class FCMRegistrationTokenDto(
    @SerializedName("device_token") val device_token: String,
    @SerializedName("is_client_push_allowed") val is_client_push_allowed: Boolean,
    @SerializedName("platform") val platform: String
)
