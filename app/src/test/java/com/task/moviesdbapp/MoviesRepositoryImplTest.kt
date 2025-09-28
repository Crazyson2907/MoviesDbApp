package com.task.moviesdbapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.task.moviesdbapp.data.local.AppDatabase
import com.task.moviesdbapp.data.local.MovieDao
import com.task.moviesdbapp.data.remote.MoviesRepositoryImpl
import com.task.moviesdbapp.domain.cache.core.MoviesRepository
import com.task.moviesdbapp.domain.network.core.TmdbApi
import com.task.moviesdbapp.domain.network.model.MovieDto
import com.task.moviesdbapp.domain.network.model.MoviesResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import android.content.Context

private class FakeApi : TmdbApi {
    override suspend fun discoverMovies(
        page: Int,
        minVote: Double,
        minVotes: Int,
        sortBy: String,
        releaseBefore: String
    ): MoviesResponse = MoviesResponse(
        page = page,
        results = listOf(
            MovieDto(
                id = 42, title = "Fake", overview = "O", releaseDate = "2023-01-02",
                posterPath = null, voteAverage = 7.8, voteCount = 123
            )
        ),
        totalPages = 1
    )
}

@get:Rule
val main = MainDispatcherRule()

class MoviesRepositoryImplTest {
    @Test
    fun upserts_and_observes() = runBlocking {
        val ctx = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(ctx, AppDatabase::class.java).build()
        val dao: MovieDao = db.movieDao()
        val api = FakeApi()
        val repo: MoviesRepository = MoviesRepositoryImpl(dao, api)

        repo.fetchPage(1)
        val list = repo.observeAll().first()
        assertEquals(1, list.size)
        assertEquals(42, list.first().id)
    }
}