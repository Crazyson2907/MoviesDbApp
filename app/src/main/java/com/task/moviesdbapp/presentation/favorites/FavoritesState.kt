package com.task.moviesdbapp.presentation.favorites

import com.task.moviesdbapp.domain.core.model.Movie

data class FavoritesState(
    val items: List<Movie> = emptyList(),
    val error: String? = null
)