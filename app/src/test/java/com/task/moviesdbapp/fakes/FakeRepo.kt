package com.task.moviesdbapp.fakes

import com.task.moviesdbapp.domain.cache.core.MoviesRepository
import com.task.moviesdbapp.domain.core.model.Movie
import com.task.moviesdbapp.domain.core.model.PagingMeta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class FakeRepo(
    initial: List<Movie> = emptyList(),
    private val totalPages: Int = 2
) : MoviesRepository {

    private val _all = MutableStateFlow(initial)

    override fun observeAll(): Flow<List<Movie>> = _all.asStateFlow()

    override fun observeFavorites(): Flow<List<Movie>> =
        _all.map { it.filter { m -> m.isFavorite } }

    override suspend fun fetchPage(page: Int): Result<PagingMeta> {
        val id = page * 100
        _all.value = _all.value + Movie(
            id = id,
            title = "Title $id",
            overview = "",
            posterPath = null,
            releaseDate = null,
            voteAverage = 7.5,
            voteCount = 100,
            isFavorite = false
        )
        return Result.success(PagingMeta(page = page, totalPages = totalPages))
    }

    override suspend fun toggleFavorite(movieId: Int, favorite: Boolean): Result<Unit> {
        _all.value = _all.value.map { if (it.id == movieId) it.copy(isFavorite = favorite) else it }
        return Result.success(Unit)
    }
}