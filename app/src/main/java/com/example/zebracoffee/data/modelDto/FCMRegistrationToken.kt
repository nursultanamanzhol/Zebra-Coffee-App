package com.example.zebracoffee.data.modelDto


class FCMRegistrationToken(
    val device_token: String,
    val is_client_push_allowed: Boolean,
    val platform: String
)