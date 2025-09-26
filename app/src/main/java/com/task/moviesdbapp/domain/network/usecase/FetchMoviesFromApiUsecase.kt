package com.task.moviesdbapp.domain.network.usecase

import com.task.moviesdbapp.domain.cache.core.MoviesRepository
import javax.inject.Inject

class FetchMoviesFromApiUseCase @Inject constructor(
    private val repo: MoviesRepository
) {
    suspend operator fun invoke(page: Int) = repo.fetchPage(page)
}