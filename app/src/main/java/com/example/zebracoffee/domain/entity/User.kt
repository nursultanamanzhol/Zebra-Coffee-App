package com.example.zebracoffee.domain.entity

interface User {
    val avatar_image: String
    val birth_date: String
    val created_at: String
    val discount_percentage: Int
    val name: String
    val phone: String
    val pk: Int
    val ref_code: String
    val status: String
    val zc_balance: Int
}