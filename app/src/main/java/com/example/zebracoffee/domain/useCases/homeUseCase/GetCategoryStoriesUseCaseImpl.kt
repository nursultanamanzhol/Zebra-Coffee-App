package com.example.zebracoffee.domain.useCases.homeUseCase

import com.example.zebracoffee.data.modelDto.Stories
import com.example.zebracoffee.data.modelDto.StoriesCategory
import com.example.zebracoffee.domain.repository.HomeRepository
import javax.inject.Inject

class GetCategoryStoriesUseCaseImpl @Inject constructor(
    private val repository: HomeRepository
) : GetCategoryStoriesUseCase {
    override suspend fun getStoriesCategory(bearerToken: String): List<StoriesCategory> {
        return repository.getStoriesCategory(bearerToken)
    }

    override suspend fun getOnBoardingStories(id: Int): List<Stories> {
        return  repository.getOnBoardingStories(id).stories
    }

}
