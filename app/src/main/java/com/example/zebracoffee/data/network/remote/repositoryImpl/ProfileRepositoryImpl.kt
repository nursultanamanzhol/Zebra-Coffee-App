package com.example.zebracoffee.data.network.remote.repositoryImpl

import com.example.zebracoffee.data.modelDto.LogoutResponse
import com.example.zebracoffee.data.modelDto.PhoneNumberResponse
import com.example.zebracoffee.data.modelDto.UpdateProfileBody
import com.example.zebracoffee.data.network.remote.api.ZebraCoffeeApi
import com.example.zebracoffee.domain.repository.ProfileRepository
import retrofit2.Response
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ZebraCoffeeApi,
) : ProfileRepository {

    override suspend fun deleteUserAccount(bearerToken: String) {
        val token = "Bearer $bearerToken"
        api.deleteUserAccount(token)
    }

    override suspend fun logout(bearerToken: String): Response<LogoutResponse> {
        val token = "Bearer $bearerToken"
        return api.logout(token)
    }

    override suspend fun updateInfoProfile(
        birthDate: String,
        name: String,
        bearerToken: String
    ): Response<PhoneNumberResponse> {
        val body = UpdateProfileBody(name, birthDate)
        val token = "Bearer $bearerToken"
        return api.updateInfoProfile(body,token)
    }
}