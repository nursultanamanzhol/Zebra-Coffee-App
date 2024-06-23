package com.example.zebracoffee.di

import android.app.Application
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.zebracoffee.data.network.remote.repositoryImpl.UserPreferencesRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication() : Application() {
    init {
        application = this
    }

    companion object {
        lateinit var application: HiltApplication
        private val Context.dataStore by preferencesDataStore(
            name = "user_preferences",
            corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() }
        )

        val prefs by lazy {
            UserPreferencesRepository(application.dataStore)
        }
    }

    fun areNotificationsEnabled(): Boolean {
        return NotificationManagerCompat.from(this).areNotificationsEnabled()
    }
}