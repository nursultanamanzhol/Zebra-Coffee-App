package com.example.zebracoffee.domain.useCases.menuUseCases

import com.example.zebracoffee.data.modelDto.menuDto.MenuDto

interface GetSearchProductUseCase {
    suspend fun getSearchProduct(search: String , shopId:Int,bearerToken: String): MenuDto
}