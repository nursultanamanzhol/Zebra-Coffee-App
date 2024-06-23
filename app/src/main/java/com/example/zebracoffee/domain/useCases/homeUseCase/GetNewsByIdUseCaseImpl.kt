package com.example.zebracoffee.domain.useCases.homeUseCase

import com.example.zebracoffee.data.modelDto.News
import com.example.zebracoffee.domain.repository.HomeRepository
import retrofit2.Response
import javax.inject.Inject

class GetNewsByIdUseCaseImpl @Inject constructor(
    private val repository: HomeRepository
): GetNewsByIdUseCase {
    override suspend fun getNewsById(bearerToken: String, id: Int): Response<News> {
        return repository.getNewsById(bearerToken,id)
    }
}