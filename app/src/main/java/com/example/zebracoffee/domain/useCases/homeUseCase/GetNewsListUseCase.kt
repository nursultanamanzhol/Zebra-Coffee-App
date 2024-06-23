package com.example.zebracoffee.domain.useCases.homeUseCase

import com.example.zebracoffee.data.modelDto.News

interface GetNewsListUseCase {
    suspend fun getNewsList(bearerToken: String): List<News>
}