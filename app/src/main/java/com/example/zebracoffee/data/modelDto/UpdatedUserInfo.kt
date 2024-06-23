package com.example.zebracoffee.data.modelDto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "updated_user_info")
data class UpdatedUserInfo(
    val avatar_image: String,
    val birth_date: String,
    val created_at: String,
    val discount_percentage: Int,
    val name: String,
    val phone: String,
    @PrimaryKey
    val pk: Int,
    val ref_code: String,
    val status: String,
    val zc_balance: Int
)