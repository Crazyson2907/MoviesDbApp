package com.task.moviesdbapp.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.task.moviesdbapp.data.auth.UserPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides @Singleton
    fun provideGso(@ApplicationContext ctx: Context): GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

    @Provides @Singleton
    fun provideGsc(@ApplicationContext ctx: Context, gso: GoogleSignInOptions): GoogleSignInClient =
        GoogleSignIn.getClient(ctx, gso)

    @Provides @Singleton
    fun provideUserPrefs(@ApplicationContext ctx: Context): UserPrefs = UserPrefs(ctx)
}