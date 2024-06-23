package com.example.zebracoffee.data.network.remote.repositoryImpl

import com.example.zebracoffee.data.modelDto.CheckVersionDto
import com.example.zebracoffee.data.modelDto.News
import com.example.zebracoffee.data.modelDto.QrResponseDto
import com.example.zebracoffee.data.modelDto.StoriesCategory
import com.example.zebracoffee.data.network.remote.api.ZebraCoffeeApi
import com.example.zebracoffee.domain.repository.HomeRepository
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: ZebraCoffeeApi,
) : HomeRepository {

    override suspend fun getStoriesCategory(bearerToken: String): List<StoriesCategory> {
        val token = "Bearer $bearerToken"
        return api.getStoriesList(token).results
    }

    override suspend fun getNewsById(bearerToken: String, id: Int): Response<News> {
        val token = "Bearer $bearerToken"
        return api.getNewsById(token,id)
    }

    override suspend fun getNewsList(bearerToken: String): List<News> {
        val token = "Bearer $bearerToken"
        return api.getNewsList(token).results
    }

    override suspend fun getUserQr(bearerToken: String): Response<QrResponseDto> {
        val token = "Bearer $bearerToken"
        return api.getUserQr(token)
    }

    override suspend fun getOnBoardingStories(id: Int): StoriesCategory {
        return api.getOnBoarding(id)
    }

    override suspend fun checkAppVersion(
        platform: String,
        currentVersion: String,
        bearerToken: String
    ): Response<CheckVersionDto> {
        val token = "Bearer $bearerToken"
        return api.checkAppVersion(platform,currentVersion,token)
    }

}
