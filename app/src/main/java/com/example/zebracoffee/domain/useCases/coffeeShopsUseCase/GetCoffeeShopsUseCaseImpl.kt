package com.example.zebracoffee.domain.useCases.coffeeShopsUseCase

import com.example.zebracoffee.data.modelDto.CoffeeDetails
import com.example.zebracoffee.data.modelDto.Country
import com.example.zebracoffee.domain.repository.CoffeeShopsRepository
import retrofit2.Response
import javax.inject.Inject

class GetCoffeeShopsUseCaseImpl @Inject constructor(
    private val repository: CoffeeShopsRepository
) : GetCoffeeShopsUseCase{

    override suspend fun getCountryList(bearerToken: String): List<Country> {
        return repository.getCountryList(bearerToken).results
    }

    override suspend fun getCoffeeShopByCity(bearerToken: String, id: Int, latitude: Double, longitude: Double): List<CoffeeDetails> {
        return repository.getCoffeeShopByCity(bearerToken, id, latitude, longitude)
    }

    override suspend fun getCoffeeShopDetails(bearerToken: String, id: Int): Response<CoffeeDetails> {
        return repository.getCoffeeShopDetails(bearerToken, id)
    }
}