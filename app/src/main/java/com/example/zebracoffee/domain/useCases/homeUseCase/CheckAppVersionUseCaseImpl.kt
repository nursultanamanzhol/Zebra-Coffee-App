package com.example.zebracoffee.domain.useCases.homeUseCase

import com.example.zebracoffee.data.modelDto.CheckVersionDto
import com.example.zebracoffee.domain.repository.HomeRepository
import retrofit2.Response
import javax.inject.Inject

class CheckAppVersionUseCaseImpl @Inject constructor(
    private val repository: HomeRepository
): CheckAppVersionUseCase {
    override suspend fun checkAppVersion(
        platform: String,
        currentVersion: String,
        bearerToken: String,
    ): Response<CheckVersionDto> {
        return repository.checkAppVersion(platform,currentVersion, bearerToken)
    }
}