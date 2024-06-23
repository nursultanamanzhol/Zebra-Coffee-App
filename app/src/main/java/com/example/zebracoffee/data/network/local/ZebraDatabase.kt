package com.example.zebracoffee.data.network.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zebracoffee.data.modelDto.UpdatedUserInfo

@Database(entities = [UpdatedUserInfo::class], version = 1)
abstract class ZebraDatabase: RoomDatabase() {
    abstract fun zebraDao(): ZebraCoffeeDao
}