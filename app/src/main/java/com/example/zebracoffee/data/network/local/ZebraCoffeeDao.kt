package com.example.zebracoffee.data.network.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.zebracoffee.data.modelDto.UpdatedUserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface ZebraCoffeeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: UpdatedUserInfo)

    @Query("SELECT * FROM updated_user_info")
    fun getAllUser(): Flow<List<UpdatedUserInfo>>

    @Query("DELETE FROM updated_user_info WHERE pk = :userId")
    suspend fun deleteUser(userId: Int)
}