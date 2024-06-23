package com.example.zebracoffee.domain.useCases.profileuseCase

import com.example.zebracoffee.domain.repository.ProfileRepository
import javax.inject.Inject

class DeleteAccountUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
): DeleteAccountUseCase {
    override suspend fun deleteUserAccount(bearerToken: String) {
        repository.deleteUserAccount(bearerToken)
    }
}