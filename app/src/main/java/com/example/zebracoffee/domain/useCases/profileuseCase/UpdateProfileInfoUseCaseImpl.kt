package com.example.zebracoffee.domain.useCases.profileuseCase

import com.example.zebracoffee.data.modelDto.PhoneNumberResponse
import com.example.zebracoffee.domain.repository.ProfileRepository
import retrofit2.Response
import javax.inject.Inject

class UpdateProfileInfoUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : UpdateProfileInfoUseCase {
    override suspend fun updateInfoProfile(
        name: String,
        birth_date: String,
        token: String
    ): Response<PhoneNumberResponse> {
        return repository.updateInfoProfile(name, birth_date,token)
    }
}