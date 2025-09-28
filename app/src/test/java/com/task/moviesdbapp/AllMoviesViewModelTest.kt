package com.task.moviesdbapp

import app.cash.turbine.test
import com.task.moviesdbapp.domain.cache.core.MoviesRepository
import com.task.moviesdbapp.domain.core.model.Movie
import com.task.moviesdbapp.domain.core.model.PagingMeta
import com.task.moviesdbapp.domain.core.usecase.FetchMoviesPageUseCase
import com.task.moviesdbapp.domain.core.usecase.ObserveAllMoviesUseCase
import com.task.moviesdbapp.domain.core.usecase.RefreshFirstPageUseCase
import com.task.moviesdbapp.domain.core.usecase.ToggleFavoriteUseCase
import com.task.moviesdbapp.presentation.all.AllMoviesViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

private class FakeRepo(
    initial: List<Movie> = emptyList(),
    private val totalPages: Int = 2
) : MoviesRepository {
    private val _all = MutableStateFlow(initial)
    override fun observeAll() = _all.asStateFlow()
    override fun observeFavorites() = _all.asStateFlow().let { it }
    override suspend fun fetchPage(page: Int) = Result.success(PagingMeta(page, totalPages)).also {
        val id = page * 100
        val list = _all.value.toMutableList()
        list.add(Movie(id, "Title $id", "", null, null, 7.5, 100, false))
        _all.value = list
    }
    override suspend fun toggleFavorite(movieId: Int, favorite: Boolean) = Result.success(Unit).also {
        _all.value = _all.value.map { if (it.id == movieId) it.copy(isFavorite = favorite) else it }
    }
}


class AllMoviesViewModelTest {
    @Test
    fun initial_load_and_toggle() = runTest {
        val repo = FakeRepo()
        val vm = AllMoviesViewModel(
            ObserveAllMoviesUseCase(repo),
            FetchMoviesPageUseCase(repo),
            RefreshFirstPageUseCase(repo),
            ToggleFavoriteUseCase(repo)
        )

        // initial state
        vm.container.stateFlow.test {
            var s = awaitItem()
            // initial reducer may set isLoading=true briefly, skip ahead:
            while (s.isLoading) s = awaitItem()

            // after init fetch, one item
            assertEquals(1, s.items.size)

            // toggle favorite
            vm.onToggleFavorite(s.items.first().id, true)
            s = awaitItem()
            assertEquals(true, s.items.first().isFavorite)
            cancelAndIgnoreRemainingEvents()
        }
    }
}