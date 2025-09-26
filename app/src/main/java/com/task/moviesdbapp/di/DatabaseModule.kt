package com.task.moviesdbapp.di

import android.content.Context
import androidx.room.Room
import com.task.moviesdbapp.data.local.AppDatabase
import com.task.moviesdbapp.data.local.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides @Singleton
    fun provideDb(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "movies.db")
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao()
}