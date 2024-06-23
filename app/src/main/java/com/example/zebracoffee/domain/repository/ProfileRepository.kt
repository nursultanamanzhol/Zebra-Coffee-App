package com.example.zebracoffee.domain.repository

import com.example.zebracoffee.data.modelDto.LogoutResponse
import com.example.zebracoffee.data.modelDto.PhoneNumberResponse
import retrofit2.Response

interface ProfileRepository {

    suspend fun updateInfoProfile(
        birthDate: String,
        name: String,
        bearerToken: String
    ): Response<PhoneNumberResponse>

    suspend fun logout(bearerToken: String): Response<LogoutResponse>

    suspend fun deleteUserAccount(bearerToken: String)
}