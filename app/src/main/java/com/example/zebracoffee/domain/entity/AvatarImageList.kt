package com.example.zebracoffee.domain.entity

interface AvatarImageList {
    val count: Int
    val next: Any
    val page_number: Int
    val per_page: Int
    val previous: Any
    val results: List<AvatarImage>
    val total_pages: Int
}