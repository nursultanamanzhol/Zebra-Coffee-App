package com.example.zebracoffee.domain.useCases.menuUseCases

import com.example.zebracoffee.data.modelDto.menuDto.MenuDto

interface GetSearchHistoryUseCase {
    suspend fun getSearchHistory(shopId:Int,bearerToken: String): MenuDto
}