package com.task.moviesdbapp.domain.core.model

import java.time.LocalDate

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: LocalDate?,
    val posterPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val isFavorite: Boolean
)