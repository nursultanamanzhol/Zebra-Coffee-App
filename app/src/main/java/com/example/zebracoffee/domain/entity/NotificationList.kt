package com.example.zebracoffee.domain.entity

interface NotificationList {
    val count: Int
    val next: Any?
    val page_number: Int
    val per_page: Int
    val previous: Any?
    val results: List<Notification>
    val total_pages: Int
}