package com.example.zebracoffee.data.modelDto

import com.google.gson.annotations.SerializedName

data class UpdateProfileBody(
    @SerializedName("birth_date") val birthDate: String,
    val name: String
)