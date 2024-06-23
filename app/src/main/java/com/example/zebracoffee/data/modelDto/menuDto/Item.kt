package com.example.zebracoffee.data.modelDto.menuDto

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("in_basket") val inBasket: Boolean,
    @SerializedName("in_basket_count")val inBasketCount: Int,
    val id: Int,
    val image: String?,
    val name: String?,
    val preview: String,
    val price: Double?,
    val state: String,
    val type: String
)