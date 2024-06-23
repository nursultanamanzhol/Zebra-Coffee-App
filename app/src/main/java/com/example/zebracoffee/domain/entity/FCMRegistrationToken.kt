package com.example.zebracoffee.domain.entity

interface FCMRegistrationToken {
    val device_token: String
    val is_client_push_allowed: Boolean
    val platform: String
}