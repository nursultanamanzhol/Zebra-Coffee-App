package com.example.zebracoffee.domain.useCases.localUseCase

import com.example.zebracoffee.data.modelDto.UpdatedUserInfo
import com.example.zebracoffee.data.modelDto.UserDto
import com.example.zebracoffee.domain.repository.LocalRepository
import javax.inject.Inject

class SaveUserDataLocalUseCaseImpl @Inject constructor(
    private val repository: LocalRepository,
) : SaveUserDataLocalUseCase {
    override suspend fun insertAll(users: UpdatedUserInfo) {
        return repository.insertAll(users)
    }
}
