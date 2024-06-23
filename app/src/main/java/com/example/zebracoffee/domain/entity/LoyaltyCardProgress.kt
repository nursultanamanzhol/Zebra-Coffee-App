package com.example.zebracoffee.domain.entity

interface LoyaltyCardProgress {
    val id: Int
    val name: String
    val discountPercentage: Int
    val threshold: Int
    val progressPercentage: Int
    val isCurrent: Boolean
    val image: String
}

