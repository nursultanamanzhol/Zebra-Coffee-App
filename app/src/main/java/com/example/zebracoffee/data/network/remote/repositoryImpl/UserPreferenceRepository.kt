package com.example.zebracoffee.data.network.remote.repositoryImpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private object Keys {
        val theme = stringPreferencesKey("theme")
    }

    val theme: Flow<AppTheme> = dataStore.data
        .map {
            AppTheme.fromPref(it[Keys.theme])
        }
        .distinctUntilChanged()

    suspend fun changeTheme(theme: AppTheme) {
        dataStore.edit {
            it[Keys.theme] = theme.toPref()
        }
    }
}