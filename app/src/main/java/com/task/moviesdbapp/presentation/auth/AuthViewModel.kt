package com.task.moviesdbapp.presentation.auth

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.task.moviesdbapp.data.auth.UserPrefs
import com.task.moviesdbapp.domain.core.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val gsc: GoogleSignInClient,
    private val prefs: UserPrefs
) : ViewModel(), ContainerHost<AuthState, Nothing> {

    override val container = container<AuthState, Nothing>(AuthState())

    init {
        val acct = GoogleSignIn.getLastSignedInAccount(gsc.applicationContext)
        viewModelScope.launch {
            val profile = acct?.let {
                UserProfile(it.displayName, it.email, it.photoUrl?.toString())
            }
            intent { reduce { state.copy(profile = profile, signedIn = profile != null) } }
        }
    }

    fun signInIntent(): Intent = gsc.signInIntent

    fun handleSignInResult(data: Intent?) = viewModelScope.launch {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        runCatching { task.result }.onSuccess { acct ->
            val p = UserProfile(acct.displayName, acct.email, acct.photoUrl?.toString())
            prefs.save(p)
            intent { reduce { state.copy(profile = p, signedIn = true) } }
        }
    }

    fun signOut() = viewModelScope.launch {
        gsc.signOut().addOnCompleteListener {
            viewModelScope.launch {
                prefs.save(null)
                intent { reduce { state.copy(profile = null, signedIn = false) } }
            }
        }
    }
}