package com.example.zebracoffee.domain.useCases.homeUseCase

import com.example.zebracoffee.data.modelDto.QrResponseDto
import retrofit2.Response

interface GetUserQrUseCase {
    suspend fun getUserQr(bearerToken: String): Response<QrResponseDto>
}