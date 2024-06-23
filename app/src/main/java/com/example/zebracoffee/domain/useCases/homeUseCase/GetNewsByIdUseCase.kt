package com.example.zebracoffee.domain.useCases.homeUseCase

import com.example.zebracoffee.data.modelDto.News
import retrofit2.Response

interface GetNewsByIdUseCase {
    suspend fun getNewsById(bearerToken: String,id: Int): Response<News>

}