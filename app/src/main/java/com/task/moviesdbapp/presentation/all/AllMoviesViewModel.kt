package com.task.moviesdbapp.presentation.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.moviesdbapp.domain.core.usecase.FetchMoviesPageUseCase
import com.task.moviesdbapp.domain.core.usecase.ObserveAllMoviesUseCase
import com.task.moviesdbapp.domain.core.usecase.RefreshFirstPageUseCase
import com.task.moviesdbapp.domain.core.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AllMoviesViewModel @Inject constructor(
    private val observeAll: ObserveAllMoviesUseCase,
    private val fetchPage: FetchMoviesPageUseCase,
    private val refreshFirst: RefreshFirstPageUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase
) : ViewModel(), ContainerHost<AllMoviesState, Nothing> {

    override val container = container<AllMoviesState, Nothing>(AllMoviesState())

    init {
        viewModelScope.launch {
            intent { reduce { state.copy(isLoading = true, error = null) } }
            fetchPage(1).onFailure { e ->
                intent { reduce { state.copy(error = e.message, isLoading = false) } }
            }.onSuccess {
                intent { reduce { state.copy(isLoading = false) } }
            }
        }
        viewModelScope.launch {
            observeAll().collect { list ->
                intent { reduce { state.copy(items = list) } }
            }
        }
    }

    fun onRefresh() = viewModelScope.launch {
        intent { reduce { state.copy(isRefreshing = true, error = null) } }
        refreshFirst().onFailure { e ->
            intent { reduce { state.copy(error = e.message) } }
        }
        intent { reduce { state.copy(isRefreshing = false) } }
    }

    fun onToggleFavorite(id: Int, toFav: Boolean) = viewModelScope.launch {
        toggleFavorite(id, toFav)
    }
}