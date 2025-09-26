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

    private var nextPage = 1
    private var totalPages = Int.MAX_VALUE
    private var loadingMoreGuard = false

    init {
        viewModelScope.launch {
            intent { reduce { state.copy(isLoading = true, error = null) } }
            fetchPage(1)
                .onSuccess { meta ->
                    nextPage = meta.page + 1
                    totalPages = meta.totalPages
                    intent { reduce { state.copy(isLoading = false) } }
                }
                .onFailure { e ->
                    intent { reduce { state.copy(isLoading = false, error = e.message) } }
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
        refreshFirst()
            .onSuccess { meta ->
                nextPage = meta.page + 1
                totalPages = meta.totalPages
            }
            .onFailure { e ->
                intent { reduce { state.copy(error = e.message) } }
            }
        intent { reduce { state.copy(isRefreshing = false) } }
    }

    fun onLoadMore() {
        if (loadingMoreGuard) return
        if (nextPage > totalPages) return
        loadingMoreGuard = true
        viewModelScope.launch {
            intent { reduce { state.copy(isLoadingMore = true) } }
            fetchPage(nextPage)
                .onSuccess { meta ->
                    nextPage = meta.page + 1
                    totalPages = meta.totalPages
                }
            intent { reduce { state.copy(isLoadingMore = false) } }
            loadingMoreGuard = false
        }
    }

    fun onToggleFavorite(id: Int, toFav: Boolean) = viewModelScope.launch {
        toggleFavorite(id, toFav)
    }
}