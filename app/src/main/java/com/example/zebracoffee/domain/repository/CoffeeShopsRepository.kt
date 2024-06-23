package com.example.zebracoffee.domain.repository

import com.example.zebracoffee.data.modelDto.CoffeeDetails
import com.example.zebracoffee.data.modelDto.ReviewList
import com.example.zebracoffee.data.modelDto.ReviewListItem
import com.example.zebracoffee.data.modelDto.ShopCountry
import com.example.zebracoffee.data.modelDto.menuDto.MenuDto
import retrofit2.Response

interface CoffeeShopsRepository {
    suspend fun getCountryList(bearerToken: String): ShopCountry

    suspend fun getCoffeeShopByCity(
        bearerToken: String,
        id: Int,
        latitude: Double,
        longitude: Double
    ): List<CoffeeDetails>

    suspend fun getCoffeeShopDetails(bearerToken: String, id: Int): Response<CoffeeDetails>

    suspend fun getReviewByShopId(shopId: Int, bearerToken: String): ReviewList

    suspend fun getMenuByShopId(shopId: Int, bearerToken: String): MenuDto

    suspend fun getSearchHistory(shopId: Int, bearerToken: String): MenuDto

    suspend fun getSearchProduct(search: String, shopId: Int, bearerToken: String): MenuDto

    suspend fun createNewRequest(
        bearerToken: String,
        grade: Int,
        text: String,
        shopId: Int,
    ): Response<ReviewListItem>

    suspend fun updateReview(
        bearerToken: String,
        grade: Int,
        text: String,
        shopId: Int,
        id: Int
    ): Response<ReviewListItem>
}

