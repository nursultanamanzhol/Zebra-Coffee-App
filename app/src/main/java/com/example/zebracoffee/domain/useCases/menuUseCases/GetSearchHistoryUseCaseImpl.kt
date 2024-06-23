package com.example.zebracoffee.domain.useCases.menuUseCases

import com.example.zebracoffee.data.modelDto.menuDto.MenuDto
import com.example.zebracoffee.domain.repository.CoffeeShopsRepository
import javax.inject.Inject

class GetSearchHistoryUseCaseImpl @Inject constructor(
    private val repository: CoffeeShopsRepository
) : GetSearchHistoryUseCase {
    override suspend fun getSearchHistory(shopId: Int, bearerToken: String): MenuDto {
        return repository.getSearchHistory(shopId, bearerToken)
    }
}
