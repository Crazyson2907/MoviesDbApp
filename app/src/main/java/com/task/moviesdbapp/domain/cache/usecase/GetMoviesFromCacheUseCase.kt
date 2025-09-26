package com.task.moviesdbapp.domain.cache.usecase

import com.task.moviesdbapp.domain.cache.core.MoviesRepository
import com.task.moviesdbapp.domain.core.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesFromCacheUseCase @Inject constructor(
    private val repo: MoviesRepository
) {
    operator fun invoke(): Flow<List<Movie>> = repo.observeAll()
}