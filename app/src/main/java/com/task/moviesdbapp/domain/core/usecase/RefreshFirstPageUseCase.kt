package com.task.moviesdbapp.domain.core.usecase

import com.task.moviesdbapp.domain.cache.core.MoviesRepository
import javax.inject.Inject

class RefreshFirstPageUseCase @Inject constructor(
    private val repo: MoviesRepository
) { suspend operator fun invoke() = repo.fetchPage(1) }