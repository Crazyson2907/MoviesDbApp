package com.task.moviesdbapp.di

import com.squareup.moshi.Moshi
import com.task.moviesdbapp.BuildConfig
import com.task.moviesdbapp.data.remote.ApiKeyInterceptor
import com.task.moviesdbapp.domain.network.core.TmdbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides @Singleton fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
            .build()

    @Provides @Singleton fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

    @Provides @Singleton fun provideApi(retrofit: Retrofit): TmdbApi =
        retrofit.create(TmdbApi::class.java)
}