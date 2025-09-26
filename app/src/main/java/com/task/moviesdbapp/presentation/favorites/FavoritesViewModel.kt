package com.task.moviesdbapp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.moviesdbapp.domain.core.usecase.ObserveFavoritesUseCase
import com.task.moviesdbapp.domain.core.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val observeFavorites: ObserveFavoritesUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase
) : ViewModel(), ContainerHost<FavoritesState, Nothing> {

    override val container = container<FavoritesState, Nothing>(FavoritesState())

    init {
        viewModelScope.launch {
            observeFavorites().onEach { list ->
                intent { reduce { state.copy(items = list) } }
            }.collect { /* no-op */ }
        }
    }

    fun onToggleFavorite(id: Int, toFav: Boolean) = viewModelScope.launch {
        toggleFavorite(id, toFav)
    }
}