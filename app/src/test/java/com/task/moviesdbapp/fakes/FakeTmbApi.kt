package com.task.moviesdbapp.fakes

import com.task.moviesdbapp.domain.network.core.TmdbApi
import com.task.moviesdbapp.domain.network.model.MovieDto
import com.task.moviesdbapp.domain.network.model.MoviesResponse

class FakeTmdbApi(
    private val totalPages: Int = 2
) : TmdbApi {
    override suspend fun discoverMovies(
        page: Int,
        minVote: Double,
        minVotes: Int,
        sortBy: String,
        releaseBefore: String
    ): MoviesResponse {
        val id = page * 100
        return MoviesResponse(
            page = page,
            results = listOf(
                MovieDto(
                    id = id, title = "Title $id", overview = "O$id",
                    releaseDate = "2024-01-01", posterPath = null,
                    voteAverage = 7.8, voteCount = 123
                )
            ),
            totalPages = totalPages
        )
    }
}