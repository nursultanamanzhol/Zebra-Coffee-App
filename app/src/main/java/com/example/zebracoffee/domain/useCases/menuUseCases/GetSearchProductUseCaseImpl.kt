package com.example.zebracoffee.domain.useCases.menuUseCases

import com.example.zebracoffee.data.modelDto.menuDto.MenuDto
import com.example.zebracoffee.domain.repository.CoffeeShopsRepository
import javax.inject.Inject

class GetSearchProductUseCaseImpl @Inject constructor(
    private val repository: CoffeeShopsRepository
) : GetSearchProductUseCase {
    override suspend fun getSearchProduct( search: String, shopId: Int, bearerToken: String): MenuDto {
        return repository.getSearchProduct(search,shopId, bearerToken)
    }
}