package com.task.moviesdbapp.domain.core.usecase

import com.task.moviesdbapp.domain.core.model.Movie
import com.task.moviesdbapp.domain.cache.core.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavoritesUseCase @Inject constructor(
    private val repo: MoviesRepository
) { operator fun invoke(): Flow<List<Movie>> = repo.observeFavorites() }