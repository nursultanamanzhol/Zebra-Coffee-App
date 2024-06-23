package com.example.zebracoffee.domain.useCases.homeUseCase

import com.example.zebracoffee.data.modelDto.Stories
import com.example.zebracoffee.data.modelDto.StoriesCategory

interface GetCategoryStoriesUseCase {
    suspend fun getStoriesCategory(bearerToken: String): List<StoriesCategory>
    suspend fun getOnBoardingStories (id: Int): List<Stories>
}