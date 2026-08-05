package com.corevo.main.system

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.corevo.main.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = Constants.DATASTORE_NAME)

class SessionManager(private val context: Context) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey(Constants.TOKEN_KEY)
        private val USERNAME_KEY = stringPreferencesKey(Constants.USERNAME_KEY)
        private val EMAIL_KEY = stringPreferencesKey(Constants.EMAIL_KEY)
    }

    val tokenFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    val usernameFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USERNAME_KEY]
    }

    val emailFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[EMAIL_KEY]
    }

    suspend fun saveSession(token: String?, username: String?, email: String?) {
        context.dataStore.edit { preferences ->
            token?.let { preferences[TOKEN_KEY] = it }
            username?.let { preferences[USERNAME_KEY] = it }
            email?.let { preferences[EMAIL_KEY] = it }
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data.first()[TOKEN_KEY]
    }

    suspend fun getUsername(): String? {
        return context.dataStore.data.first()[USERNAME_KEY]
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences.remove(USERNAME_KEY)
            preferences.remove(EMAIL_KEY)
        }
    }
}
