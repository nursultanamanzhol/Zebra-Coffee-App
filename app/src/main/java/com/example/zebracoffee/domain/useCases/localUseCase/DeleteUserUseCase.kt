package com.example.zebracoffee.domain.useCases.localUseCase

interface DeleteUserUseCase {
    suspend fun deleteUser(userId: Int)
}