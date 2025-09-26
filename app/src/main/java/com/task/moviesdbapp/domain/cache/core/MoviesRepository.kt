package com.task.moviesdbapp.domain.cache.core

import com.task.moviesdbapp.domain.core.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun observeAll(): Flow<List<Movie>>
    fun observeFavorites(): Flow<List<Movie>>
    suspend fun fetchPage(page: Int): Result<Unit>
    suspend fun toggleFavorite(movieId: Int, favorite: Boolean): Result<Unit>
}