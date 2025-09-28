package com.task.moviesdbapp.fakes

import com.task.moviesdbapp.data.local.MovieDao
import com.task.moviesdbapp.domain.cache.entity.MovieEntity
import com.task.moviesdbapp.domain.core.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeMovieDao : MovieDao {

    private val entities = MutableStateFlow<List<MovieEntity>>(emptyList())

    override fun observeAll(): Flow<List<Movie>> =
        entities.map { list ->
            list.sortedWith(sqlLikeComparator()).map { it.toDomain() }
        }

    override fun observeFavorites(): Flow<List<Movie>> =
        entities.map { list ->
            list.filter { it.isFavorite }
                .sortedWith(sqlLikeComparator())
                .map { it.toDomain() }
        }

    override suspend fun upsertAll(list: List<MovieEntity>) {
        val byId = entities.value.associateBy { it.id }.toMutableMap()
        list.forEach { incoming ->
            val keepFav = byId[incoming.id]?.isFavorite ?: false
            byId[incoming.id] = incoming.copy(isFavorite = keepFav)
        }
        entities.value = byId.values.toList()
    }

    override suspend fun setFavorite(movieId: Int, favorite: Boolean) {
        entities.value = entities.value.map { e ->
            if (e.id == movieId) e.copy(isFavorite = favorite) else e
        }
    }

    override suspend fun getFavoriteIds(): List<Int> =
        entities.value.filter { it.isFavorite }.map { it.id }

    private fun sqlLikeComparator() = compareBy<MovieEntity>(
        { it.releaseDate == null }
    ).thenByDescending { it.releaseDate }
        .thenByDescending { it.id }

    private fun MovieEntity.toDomain(): Movie = Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        voteAverage = voteAverage,
        voteCount = voteCount,
        isFavorite = isFavorite
    )
}