package com.example.zebracoffee.data.network.local

import com.example.zebracoffee.data.mapper.UserInfoMapper
import com.example.zebracoffee.data.modelDto.UpdatedUserInfo
import com.example.zebracoffee.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val dao: ZebraCoffeeDao,
    private val mapper: UserInfoMapper,
) : LocalRepository {
    override suspend fun insertAll(users: UpdatedUserInfo) {
        dao.insertAll(users)
    }
    override fun getAllUser(): Flow<List<UpdatedUserInfo>> =
        dao.getAllUser().map { userList ->
            userList.map { user ->
                mapper.mapEntityToDbModel(user)
            }
        }
    override suspend fun deleteUser(userId: Int) {
        dao.deleteUser(userId)
    }
}
