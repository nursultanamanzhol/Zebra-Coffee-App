package com.example.zebracoffee.data.modelDto

data class NewsList(
    val count: Int,
    val next: Any,
    val page_number: Int,
    val per_page: Int,
    val previous: Any,
    val results: List<News>,
    val total_pages: Int
)