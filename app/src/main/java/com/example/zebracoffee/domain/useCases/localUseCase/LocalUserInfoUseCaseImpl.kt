package com.example.zebracoffee.domain.useCases.localUseCase

import com.example.zebracoffee.data.modelDto.UpdatedUserInfo
import com.example.zebracoffee.data.modelDto.UserDto
import com.example.zebracoffee.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalUserInfoUseCaseImpl @Inject constructor( // TODO provide without inject
    private val repository: LocalRepository,
) : LocalUserInfoUseCase {
    override fun getAllUser(): Flow<List<UpdatedUserInfo>> {
        return repository.getAllUser()
    }
}