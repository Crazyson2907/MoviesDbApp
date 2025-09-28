package com.task.moviesdbapp

import com.task.moviesdbapp.data.remote.MoviesRepositoryImpl
import com.task.moviesdbapp.fakes.FakeMovieDao
import com.task.moviesdbapp.fakes.FakeTmdbApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class MoviesRepositoryImplTest {
    @Test
    fun fetch_inserts_and_observes() = runTest {
        val dao = FakeMovieDao()
        val api = FakeTmdbApi(totalPages = 1)
        val repo = MoviesRepositoryImpl(dao, api)

        // fetch page 1
        val meta = repo.fetchPage(1).getOrThrow()
        assertEquals(1, meta.page)
        assertEquals(1, meta.totalPages)

        // observe list
        val list = repo.observeAll().first()
        assertEquals(1, list.size)
        assertEquals(100, list.first().id)

        // toggle favorite
        repo.toggleFavorite(100, true)
        val favs = repo.observeFavorites().first()
        assertEquals(true, favs.first().isFavorite)
    }
}