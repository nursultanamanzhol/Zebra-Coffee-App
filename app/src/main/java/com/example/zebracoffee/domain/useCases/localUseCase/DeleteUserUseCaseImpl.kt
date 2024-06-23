package com.example.zebracoffee.domain.useCases.localUseCase

import com.example.zebracoffee.domain.repository.LocalRepository
import javax.inject.Inject

class DeleteUserUseCaseImpl @Inject constructor(
    private val repository: LocalRepository
): DeleteUserUseCase {
    override suspend fun deleteUser(userId: Int) {
        return repository.deleteUser(userId)
    }
}