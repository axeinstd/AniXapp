package tech.axeinstd.anixapp.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class UserStorage(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_data_store")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val NEVER_SHOW_LOGIN_SCREEN_AGAIN =
            booleanPreferencesKey("never_show_login_screen_again")
        private val USERNAME = stringPreferencesKey("username")
    }

    suspend fun saveUserId(id: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = id
        }
    }

    fun getUserId() =
        context.dataStore.data.map { prefs ->
            return@map prefs[USER_ID_KEY]
        }


    suspend fun setShowLoginScreen(needToShow: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[NEVER_SHOW_LOGIN_SCREEN_AGAIN] = needToShow
        }
    }

    fun getIfNeedToShowLoginScreen() =
        context.dataStore.data.map { prefs ->
            return@map prefs[NEVER_SHOW_LOGIN_SCREEN_AGAIN]
        }

    fun getUsername() =
        context.dataStore.data.map { prefs ->
            return@map prefs[USERNAME]
        }
    suspend fun setUsername(username: String) {
        context.dataStore.edit { prefs ->
            prefs[USERNAME] = username
        }
    }
}