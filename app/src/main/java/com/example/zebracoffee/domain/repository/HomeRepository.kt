package com.example.zebracoffee.domain.repository

import com.example.zebracoffee.data.modelDto.CheckVersionDto
import com.example.zebracoffee.data.modelDto.News
import com.example.zebracoffee.data.modelDto.QrResponseDto
import com.example.zebracoffee.data.modelDto.StoriesCategory
import retrofit2.Response

interface HomeRepository {

    suspend fun getStoriesCategory(bearerToken: String): List<StoriesCategory>
    suspend fun getNewsList(bearerToken: String): List<News>
    suspend fun getUserQr(bearerToken: String): Response<QrResponseDto>
    suspend fun getOnBoardingStories(id:Int): StoriesCategory
    suspend fun checkAppVersion(platform: String,currentVersion: String,bearerToken: String): Response<CheckVersionDto>

    suspend fun getNewsById(bearerToken: String,id: Int): Response<News>
}