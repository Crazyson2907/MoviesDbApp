package com.task.moviesdbapp

import com.task.moviesdbapp.domain.core.usecase.FetchMoviesPageUseCase
import com.task.moviesdbapp.domain.core.usecase.ObserveAllMoviesUseCase
import com.task.moviesdbapp.domain.core.usecase.RefreshFirstPageUseCase
import com.task.moviesdbapp.domain.core.usecase.ToggleFavoriteUseCase
import com.task.moviesdbapp.presentation.all.AllMoviesViewModel
import com.task.moviesdbapp.fakes.FakeRepo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class AllMoviesViewModelTest {

    @get:Rule
    val main = MainDispatcherRule()

    @Test
    fun initial_load_and_toggle() = runTest {
        val repo = FakeRepo()
        val vm = AllMoviesViewModel(
            ObserveAllMoviesUseCase(repo),
            FetchMoviesPageUseCase(repo),
            RefreshFirstPageUseCase(repo),
            ToggleFavoriteUseCase(repo)
        )

        val s1 = vm.container.stateFlow.first { it.items.isNotEmpty() }
        assertEquals(1, s1.items.size)

        vm.onToggleFavorite(s1.items.first().id, true)
        val s2 = vm.container.stateFlow.first { it.items.first().isFavorite }
        assertEquals(true, s2.items.first().isFavorite)
    }
}