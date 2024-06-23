package com.example.zebracoffee.data.network.remote.repositoryImpl

import com.example.zebracoffee.data.modelDto.CoffeeDetails
import com.example.zebracoffee.data.modelDto.CreateReviewBody
import com.example.zebracoffee.data.modelDto.ReviewList
import com.example.zebracoffee.data.modelDto.ReviewListItem
import com.example.zebracoffee.data.modelDto.ShopCountry
import com.example.zebracoffee.data.modelDto.menuDto.MenuDto
import com.example.zebracoffee.data.network.remote.api.ZebraCoffeeApi
import com.example.zebracoffee.domain.repository.CoffeeShopsRepository
import retrofit2.Response
import javax.inject.Inject

class CoffeeShopsRepositoryImpl @Inject constructor(
    private val api: ZebraCoffeeApi,
) : CoffeeShopsRepository {

    override suspend fun getCountryList(bearerToken: String): ShopCountry {
        val token = "Bearer $bearerToken"
        return api.getCountryList(token)
    }

    override suspend fun getCoffeeShopByCity(
        bearerToken: String,
        id: Int,
        latitude: Double,
        longitude: Double
    ): List<CoffeeDetails> {
        val token = "Bearer $bearerToken"
        return api.getCoffeeShopByCity(token, id, latitude, longitude)
    }

    override suspend fun getCoffeeShopDetails(
        bearerToken: String,
        id: Int
    ): Response<CoffeeDetails> {
        val token = "Bearer $bearerToken"
        return api.getCoffeeShopDetails(token, id)
    }

    override suspend fun getReviewByShopId(shopId: Int, bearerToken: String): ReviewList {
        val token = "Bearer $bearerToken"
        return api.getReviewByShopId(shopId, token)
    }

    override suspend fun createNewRequest(
        bearerToken: String,
        grade: Int,
        text: String,
        shopId: Int,
    ): Response<ReviewListItem> {
        val token = "Bearer $bearerToken"
        val body = CreateReviewBody(grade, text, shopId)
        return api.createNewReview(body, token)
    }

    override suspend fun updateReview(
        bearerToken: String,
        grade: Int,
        text: String,
        shopId: Int,
        id: Int,
    ): Response<ReviewListItem> {
        val token = "Bearer $bearerToken"
        val body = CreateReviewBody(grade, text, shopId)
        return api.updateReview(body, id, token)
    }

    override suspend fun getMenuByShopId(shopId: Int, bearerToken: String): MenuDto {
        val token = "Bearer $bearerToken"
        return api.getMenuByShopId(shopId, token)
    }
    override suspend fun getSearchHistory(shopId: Int, bearerToken: String): MenuDto {
        val token = "Bearer $bearerToken"
        return api.getSearchHistory(shopId, token)
    }

    override suspend fun getSearchProduct(
        search: String,
        shopId: Int,
        bearerToken: String
    ): MenuDto {
        val token = "Bearer $bearerToken"
        return api.getSearchProduct(search, shopId, token)
    }
}
