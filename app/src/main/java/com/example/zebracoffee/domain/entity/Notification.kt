package com.example.zebracoffee.domain.entity


interface Notification {
    val body: String
    val datetime: String
    val id: Int
    val notification_url: String?
    val title: String
    val type: Type?
}
