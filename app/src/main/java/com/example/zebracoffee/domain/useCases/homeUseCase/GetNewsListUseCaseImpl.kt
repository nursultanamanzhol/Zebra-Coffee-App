package com.example.zebracoffee.domain.useCases.homeUseCase

import com.example.zebracoffee.data.modelDto.News
import com.example.zebracoffee.domain.repository.HomeRepository
import javax.inject.Inject

class GetNewsListUseCaseImpl @Inject constructor(
    private val repository: HomeRepository
): GetNewsListUseCase {
    override suspend fun getNewsList(bearerToken: String): List<News> {
        return repository.getNewsList(bearerToken)
    }
}