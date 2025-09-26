package com.task.moviesdbapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.moviesdbapp.domain.cache.entity.MovieEntity
import com.task.moviesdbapp.domain.core.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("""
        SELECT * FROM movies
        ORDER BY (releaseDate IS NULL) ASC, releaseDate DESC, id DESC
    """)
    fun observeAll(): Flow<List<Movie>>

    @Query("""
        SELECT * FROM movies
        WHERE isFavorite = 1
        ORDER BY (releaseDate IS NULL) ASC, releaseDate DESC, id DESC
    """)
    fun observeFavorites(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(list: List<MovieEntity>)

    @Query("UPDATE movies SET isFavorite = :favorite WHERE id = :movieId")
    suspend fun setFavorite(movieId: Int, favorite: Boolean)

    @Query("SELECT id FROM movies WHERE isFavorite = 1")
    suspend fun getFavoriteIds(): List<Int>
}