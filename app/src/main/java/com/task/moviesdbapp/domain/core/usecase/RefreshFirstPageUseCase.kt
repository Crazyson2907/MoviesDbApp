package com.task.moviesdbapp.domain.core.usecase

import com.task.moviesdbapp.domain.cache.core.MoviesRepository
import com.task.moviesdbapp.domain.core.model.PagingMeta
import javax.inject.Inject

class RefreshFirstPageUseCase @Inject constructor(
    private val repo: MoviesRepository
) {
    suspend operator fun invoke(): Result<PagingMeta> = repo.fetchPage(1)
}