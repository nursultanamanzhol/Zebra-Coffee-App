package com.example.zebracoffee.domain.repository

import com.example.zebracoffee.data.modelDto.UpdatedUserInfo
import com.example.zebracoffee.data.modelDto.UserDto
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun insertAll(users: UpdatedUserInfo)
    fun getAllUser(): Flow<List<UpdatedUserInfo>>
    suspend fun deleteUser(userId: Int)

}