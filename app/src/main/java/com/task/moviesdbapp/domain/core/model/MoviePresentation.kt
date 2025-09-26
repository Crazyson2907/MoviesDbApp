package com.task.moviesdbapp.domain.core.model

import java.time.format.DateTimeFormatter

data class MoviePresentation(
    val id: Int,
    val title: String,
    val overview: String,
    val monthHeader: String,   // e.g., "Feb 2021"
    val posterPath: String?,
    val voteAverage: Double,
    val isFavorite: Boolean
)

fun Movie.toPresentation(): MoviePresentation =
    MoviePresentation(
        id = id,
        title = title,
        overview = overview,
        monthHeader = releaseDate?.format(DateTimeFormatter.ofPattern("MMM yyyy")) ?: "Unknown",
        posterPath = posterPath,
        voteAverage = voteAverage,
        isFavorite = isFavorite
    )