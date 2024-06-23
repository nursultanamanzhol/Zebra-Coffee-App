package com.example.zebracoffee.data.modelDto

import com.google.gson.annotations.SerializedName

data class VerificationCodeBody(
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("sms_code") val smsCode: Int,
)