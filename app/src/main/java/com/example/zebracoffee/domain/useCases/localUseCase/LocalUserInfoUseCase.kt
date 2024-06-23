package com.example.zebracoffee.domain.useCases.localUseCase

import com.example.zebracoffee.data.modelDto.UpdatedUserInfo
import com.example.zebracoffee.data.modelDto.UserDto
import kotlinx.coroutines.flow.Flow

interface LocalUserInfoUseCase {
    fun getAllUser(): Flow<List<UpdatedUserInfo>>
}