package com.example.zebracoffee.data.modelDto

data class ShopCountry(
    val count: Int,
    val next: Any,
    val page_number: Int,
    val per_page: Int,
    val previous: Any,
    val results: List<Country>,
    val total_pages: Int
)