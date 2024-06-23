package com.example.zebracoffee.di

import android.content.Context
import androidx.room.Room
import com.example.zebracoffee.data.network.local.ZebraCoffeeDao
import com.example.zebracoffee.data.network.local.ZebraDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {
    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext context: Context): ZebraDatabase {
        return Room
            .databaseBuilder(context, ZebraDatabase::class.java, "user_info")
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    fun getZebraDao(db: ZebraDatabase): ZebraCoffeeDao = db.zebraDao()
}