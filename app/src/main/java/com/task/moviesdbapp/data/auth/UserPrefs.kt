package com.task.moviesdbapp.data.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.task.moviesdbapp.domain.core.model.UserProfile
import kotlinx.coroutines.flow.map

private val Context.userDataStore by preferencesDataStore("user_prefs")

object UserKeys {
    val displayName = stringPreferencesKey("displayName")
    val email = stringPreferencesKey("email")
    val photoUrl = stringPreferencesKey("photoUrl")
}

class UserPrefs(private val context: Context) {
    val userFlow = context.userDataStore.data.map { p ->
        UserProfile(
            displayName = p[UserKeys.displayName],
            email = p[UserKeys.email],
            photoUrl = p[UserKeys.photoUrl]
        )
    }

    suspend fun save(profile: UserProfile?) {
        context.userDataStore.edit { p ->
            if (profile == null) {
                p.remove(UserKeys.displayName); p.remove(UserKeys.email); p.remove(UserKeys.photoUrl)
            } else {
                p[UserKeys.displayName] = profile.displayName.orEmpty()
                p[UserKeys.email] = profile.email.orEmpty()
                p[UserKeys.photoUrl] = profile.photoUrl.orEmpty()
            }
        }
    }
}