package com.csd3156.project1.database

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException


/**
 * This data class holds the preference settings that are saved in the DataStore. It is exposed
 * via the Flow interface.
 */
data class UserPreferences(val ShowGrid: Boolean)

/**
 * Class that handles saving and retrieving user preferences, utilizing Preferences DataStore. This
 * class may be utilized in either the ViewModel or an Activity, depending on what preferences are
 * being saved.
 */
class UserPreferencesRepository(context: Context) {

    // TODO Create the Preferences DataStore
    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    private val dataStore = context.dataStore

    // TODO Create a private object for defining PreferencesKeys
    private object PreferencesKeys {
        val showGrid = booleanPreferencesKey("show_grid")
    }

    // TODO Add code to get the user preferences flow
    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data.catch {
        exception ->
        if (exception is IOException)
            emit(emptyPreferences())
        else
            throw exception
    }.map {preferences ->
        val showGrid = preferences[PreferencesKeys.showGrid]?: false
        UserPreferences(showGrid)
    }

    // TODO link this class somehow with the rest of your classes

    // TODO Add public 'update' function(s)
    //      To be called (from an Activity) whenever a preference setting is changed.
    //      This function should call DataStore.edit() to persist the new setting.
    suspend fun save(showGrid: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.showGrid] = showGrid
        }
    }
    suspend fun read() : Boolean? {
        val preferences = dataStore.data.first()
        return preferences[PreferencesKeys.showGrid]
    }

    companion object {
        // Constant for naming our DataStore - you can change this if you want
        private const val USER_PREFERENCES_NAME = "user_preferences"

        // The usual for debugging
        private val TAG: String = "UserPreferencesRepository"

        // Boilerplate-y code for singleton: the private reference to this self
        @Volatile
        private var INSTANCE: UserPreferencesRepository? = null

        /**
         * Boilerplate-y code for singleton: to ensure only a single copy is ever present
         * @param context to init the datastore
         */
        fun getInstance(context: Context): UserPreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = UserPreferencesRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}