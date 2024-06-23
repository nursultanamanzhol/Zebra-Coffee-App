package com.example.zebracoffee.data.modelDto

import com.example.zebracoffee.domain.entity.User

data class UserDto(
    override val avatar_image: String,
    override val birth_date: String,
    override val created_at: String,
    override val discount_percentage: Int,
    override var name: String,
    override val phone: String,
    override val pk: Int,
    override val ref_code: String,
    override val status: String,
    override val zc_balance: Int
) : User