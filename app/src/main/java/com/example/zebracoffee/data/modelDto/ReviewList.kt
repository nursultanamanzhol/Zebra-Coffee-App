package com.example.zebracoffee.data.modelDto

data class ReviewList(
    val count: Int,
    val next: String,
    val page_number: Int,
    val per_page: Int,
    val previous: Any,
    val results: List<ReviewListItem>,
    val total_pages: Int
)