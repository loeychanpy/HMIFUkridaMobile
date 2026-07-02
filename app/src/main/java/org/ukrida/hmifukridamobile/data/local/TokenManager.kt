package org.ukrida.hmifukridamobile.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

class TokenManager(private val context: Context) {

    companion object {
        private val TOKEN_KEY   = stringPreferencesKey("token")
        private val ROLE_KEY    = stringPreferencesKey("role")
        private val NAME_KEY    = stringPreferencesKey("name")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { it[TOKEN_KEY] = token }
    }

    suspend fun getToken(): String? =
        context.dataStore.data.map { it[TOKEN_KEY] }.first()

    suspend fun saveRole(role: String) {
        context.dataStore.edit { it[ROLE_KEY] = role }
    }

    suspend fun getRole(): String? =
        context.dataStore.data.map { it[ROLE_KEY] }.first()

    suspend fun saveName(name: String) {
        context.dataStore.edit { it[NAME_KEY] = name }
    }

    suspend fun getName(): String? =
        context.dataStore.data.map { it[NAME_KEY] }.first()

    suspend fun saveUserId(id: Int) {
        context.dataStore.edit { it[USER_ID_KEY] = id.toString() }
    }

    suspend fun getUserId(): Int? =
        context.dataStore.data.map { it[USER_ID_KEY]?.toIntOrNull() }.first()

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
