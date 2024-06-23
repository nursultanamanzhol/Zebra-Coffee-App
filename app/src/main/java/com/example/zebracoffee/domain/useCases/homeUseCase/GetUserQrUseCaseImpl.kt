package com.example.zebracoffee.domain.useCases.homeUseCase

import com.example.zebracoffee.data.modelDto.QrResponseDto
import com.example.zebracoffee.domain.repository.HomeRepository
import retrofit2.Response
import javax.inject.Inject

class GetUserQrUseCaseImpl @Inject constructor(
    private val repository: HomeRepository
): GetUserQrUseCase {
    override suspend fun getUserQr(bearerToken: String): Response<QrResponseDto> {
        return repository.getUserQr(bearerToken)
    }
}