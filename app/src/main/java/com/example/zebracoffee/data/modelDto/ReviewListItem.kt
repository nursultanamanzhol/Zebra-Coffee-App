package com.example.zebracoffee.data.modelDto

data class ReviewListItem(
    val avatar: String,
    val client: Int,
    val client_phone_number: String,
    val date: String,
    val grade: Int,
    val id: Int,
    val my_review: Boolean,
    val shop: Int,
    val shop_name: String,
    val shop_rating: Int,
    val status: String,
    val text: String,
    val type: String
)