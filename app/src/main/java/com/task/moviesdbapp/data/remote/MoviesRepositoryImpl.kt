package com.task.moviesdbapp.data.remote

import com.task.moviesdbapp.data.local.MovieDao
import com.task.moviesdbapp.domain.cache.core.MoviesRepository
import com.task.moviesdbapp.domain.cache.entity.MovieEntity
import com.task.moviesdbapp.domain.core.model.Movie
import com.task.moviesdbapp.domain.network.core.TmdbApi
import com.task.moviesdbapp.domain.network.model.MoviesResponse
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val dao: MovieDao,
    private val api: TmdbApi
) : MoviesRepository {

    override fun observeAll(): Flow<List<Movie>> = dao.observeAll()
    override fun observeFavorites(): Flow<List<Movie>> = dao.observeFavorites()

    override suspend fun fetchPage(page: Int): Result<Unit> = runCatching {
        val today = LocalDate.now().toString()
        val resp: MoviesResponse = api.discoverMovies(page = page, releaseBefore = today)
        val favoriteIds = dao.getFavoriteIds().toSet()
        val entities = resp.results.map { dto ->
            MovieEntity(
                id = dto.id,
                title = dto.title.orEmpty(),
                overview = dto.overview.orEmpty(),
                releaseDate = dto.releaseDate?.let { runCatching { LocalDate.parse(it) }.getOrNull() },
                posterPath = dto.posterPath,
                voteAverage = dto.voteAverage ?: 0.0,
                voteCount = dto.voteCount ?: 0,
                isFavorite = dto.id in favoriteIds
            )
        }
        dao.upsertAll(entities)
    }

    override suspend fun toggleFavorite(movieId: Int, favorite: Boolean): Result<Unit> =
        runCatching { dao.setFavorite(movieId, favorite) }
}