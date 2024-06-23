package com.example.zebracoffee.domain.useCases.localUseCase

import com.example.zebracoffee.data.modelDto.UpdatedUserInfo

interface SaveUserDataLocalUseCase {
    suspend fun insertAll(users: UpdatedUserInfo)
}