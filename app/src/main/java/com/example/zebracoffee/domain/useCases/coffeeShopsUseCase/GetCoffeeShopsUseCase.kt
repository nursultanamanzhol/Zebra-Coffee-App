package com.example.zebracoffee.domain.useCases.coffeeShopsUseCase

import com.example.zebracoffee.data.modelDto.CoffeeDetails
import com.example.zebracoffee.data.modelDto.Country
import retrofit2.Response

interface GetCoffeeShopsUseCase {

    suspend fun getCountryList(bearerToken: String): List<Country>

    suspend fun getCoffeeShopByCity(bearerToken: String, id:Int, latitude: Double, longitude: Double): List<CoffeeDetails>

    suspend fun getCoffeeShopDetails(bearerToken: String, id:Int): Response<CoffeeDetails>

}