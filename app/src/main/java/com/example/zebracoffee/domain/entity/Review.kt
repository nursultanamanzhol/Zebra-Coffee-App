package com.example.zebracoffee.domain.entity

interface Review {
    val avatar: String
    val client: Int
    val date: String
    val grade: Int
    val id: Int
    val myReview: Boolean
    val shop: Int
    val shopRating: Double
    val text: String
    val type: String
}