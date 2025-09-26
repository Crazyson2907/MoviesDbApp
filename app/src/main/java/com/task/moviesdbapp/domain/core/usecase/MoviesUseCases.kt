package com.task.moviesdbapp.domain.core.usecase

data class MoviesUseCases(
    val observeAll: ObserveAllMoviesUseCase,
    val observeFavorites: ObserveFavoritesUseCase,
    val fetchPage: FetchMoviesPageUseCase,
    val refreshFirst: RefreshFirstPageUseCase,
    val toggleFavorite: ToggleFavoriteUseCase
)