package com.example.zebracoffee.data.modelDto

import com.google.gson.annotations.SerializedName
import com.example.zebracoffee.domain.entity.Type

data class Type(
    @SerializedName("id")override val id: Int,
    @SerializedName("name")override val name: String,
    @SerializedName("image")override val image: String,
): Type
