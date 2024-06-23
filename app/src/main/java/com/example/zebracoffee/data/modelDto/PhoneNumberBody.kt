package com.example.zebracoffee.data.modelDto

import com.google.gson.annotations.SerializedName

data class PhoneNumberBody(
    @SerializedName("phone_number") val phoneNumber: String
)