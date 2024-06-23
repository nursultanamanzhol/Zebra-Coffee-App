package com.example.zebracoffee.data.modelDto

import androidx.annotation.Keep

@Keep
data class City(
    val country: Int,
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
)