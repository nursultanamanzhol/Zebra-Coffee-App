package com.example.zebracoffee.domain.useCases.profileuseCase

interface DeleteAccountUseCase {
    suspend fun deleteUserAccount(bearerToken: String)

}