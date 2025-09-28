package com.task.moviesdbapp.presentation.auth

import com.task.moviesdbapp.domain.core.model.UserProfile

data class AuthState(
    val profile: UserProfile? = null,
    val signedIn: Boolean = false
)