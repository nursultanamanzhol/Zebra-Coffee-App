package com.example.zebracoffee.domain.useCases.homeUseCase

import com.example.zebracoffee.data.modelDto.CheckVersionDto
import retrofit2.Response

interface CheckAppVersionUseCase {
    suspend fun checkAppVersion(
        platform: String,
        currentVersion: String,
        bearerToken: String,
    ): Response<CheckVersionDto>
}