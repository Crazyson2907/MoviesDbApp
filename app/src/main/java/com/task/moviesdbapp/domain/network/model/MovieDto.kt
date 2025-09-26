package com.task.moviesdbapp.domain.network.model

import com.squareup.moshi.Json

data class MovieDto(
    val id: Int,
    val title: String?,
    val overview: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "vote_count") val voteCount: Int?
)