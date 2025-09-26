package com.task.moviesdbapp.domain.core.usecase

import com.task.moviesdbapp.domain.cache.core.MoviesRepository
import com.task.moviesdbapp.domain.core.model.PagingMeta
import javax.inject.Inject

class FetchMoviesPageUseCase @Inject constructor(
    private val repo: MoviesRepository
) {
    suspend operator fun invoke(page: Int): Result<PagingMeta> = repo.fetchPage(page)
}