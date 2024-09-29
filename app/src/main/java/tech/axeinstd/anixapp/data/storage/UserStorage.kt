package tech.axeinstd.anixapp.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class UserStorage(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_data_store")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    suspend fun saveUserId(id: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = id
        }
    }

    fun getUserId() {
        context.dataStore.data.map { prefs ->
            return@map prefs[USER_ID_KEY]
        }
    }
}