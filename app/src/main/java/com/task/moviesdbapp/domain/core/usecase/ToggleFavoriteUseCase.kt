package com.task.moviesdbapp.domain.core.usecase


import com.task.moviesdbapp.domain.cache.core.MoviesRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repo: MoviesRepository
) { suspend operator fun invoke(id: Int, fav: Boolean) = repo.toggleFavorite(id, fav) }