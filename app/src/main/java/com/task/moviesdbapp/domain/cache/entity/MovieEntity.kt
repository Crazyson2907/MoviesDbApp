package com.task.moviesdbapp.domain.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: LocalDate?,
    val posterPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val isFavorite: Boolean = false
)