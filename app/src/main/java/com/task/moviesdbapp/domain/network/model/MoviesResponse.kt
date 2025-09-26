package com.task.moviesdbapp.domain.network.model

import com.squareup.moshi.Json

data class MoviesResponse(
    val page: Int,
    val results: List<MovieDto>,
    @Json(name = "total_pages") val totalPages: Int
)