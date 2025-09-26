package com.task.moviesdbapp.presentation.all

import com.task.moviesdbapp.domain.core.model.Movie

data class AllMoviesState(
    val items: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)