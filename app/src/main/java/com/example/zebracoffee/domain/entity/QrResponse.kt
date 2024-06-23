package com.example.zebracoffee.domain.entity

interface QrResponse {
    val active: Boolean
    val code: Int
    val creationDate: String
    val expirationDate: String
    val id: Int
    val qr: String
    val user: Int
}