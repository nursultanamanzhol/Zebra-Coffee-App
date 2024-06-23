package com.example.zebracoffee.domain.entity

data class RefreshTokenResponse(
    val access_token: String,
    val refresh_token: String
)