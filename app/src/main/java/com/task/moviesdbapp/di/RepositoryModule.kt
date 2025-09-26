package com.task.moviesdbapp.di

import com.task.moviesdbapp.data.remote.MoviesRepositoryImpl
import com.task.moviesdbapp.domain.cache.core.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton
    abstract fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository
}