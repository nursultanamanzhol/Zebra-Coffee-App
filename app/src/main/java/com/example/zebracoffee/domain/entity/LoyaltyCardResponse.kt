package com.example.zebracoffee.domain.entity

interface LoyaltyCardResponse {
    val discount_percentage: Int
    val id: Int
    val image: String
    val name: String
    val progress_percentage: Int
    val threshold: Int
}