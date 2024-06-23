package com.example.zebracoffee.domain.useCases.menuUseCases

import com.example.zebracoffee.data.modelDto.menuDto.MenuDto
import com.example.zebracoffee.domain.repository.CoffeeShopsRepository
import javax.inject.Inject

class GetMenuByShopIdUseCaseImpl @Inject constructor(
    private val repository: CoffeeShopsRepository
) : GetMenuByShopIdUseCase {
    override suspend fun getMenuByShopId(shopId: Int, bearerToken: String): MenuDto {
        return repository.getMenuByShopId(shopId, bearerToken)
    }
}

