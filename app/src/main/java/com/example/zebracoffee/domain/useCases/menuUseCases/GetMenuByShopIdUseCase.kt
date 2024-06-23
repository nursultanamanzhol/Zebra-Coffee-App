package com.example.zebracoffee.domain.useCases.menuUseCases

import com.example.zebracoffee.data.modelDto.menuDto.MenuDto

interface GetMenuByShopIdUseCase {
    suspend fun getMenuByShopId(shopId:Int,bearerToken: String): MenuDto
}
