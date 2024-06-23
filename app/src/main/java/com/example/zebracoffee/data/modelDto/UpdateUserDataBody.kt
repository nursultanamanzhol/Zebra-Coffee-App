package com.example.zebracoffee.data.modelDto

import com.google.gson.annotations.SerializedName

data class UpdateUserDataBody(
    val name: String,
    val phone: String,
    @SerializedName("birth_date") val birthDate: String,
    @SerializedName("avatar_id") val avatarId: Int,
)