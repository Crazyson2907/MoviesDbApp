package com.task.moviesdbapp.domain.network.core

import com.task.moviesdbapp.domain.network.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * We use /discover/movie with filters:
 * - vote_average.gte=7.0
 * - vote_count.gte=100
 * - sort_by=primary_release_date.desc
 * - primary_release_date.lte=today (defaulted to today string)
 */
interface TmdbApi {
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int,
        @Query("vote_average.gte") minVote: Double = 7.0,
        @Query("vote_count.gte") minVotes: Int = 100,
        @Query("sort_by") sortBy: String = "primary_release_date.desc",
        @Query("primary_release_date.lte") releaseBefore: String
    ): MoviesResponse
}