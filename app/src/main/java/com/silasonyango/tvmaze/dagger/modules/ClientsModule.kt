package com.silasonyango.tvmaze.dagger.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.silasonyango.tvmaze.TvMazeRepository
import com.silasonyango.tvmaze.TvMazeService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Module
class ClientsModule {

    @Provides
    @Inject
    fun provideTvMazeService(
        httpClient: OkHttpClient,
        gson: Gson,
    ): TvMazeService {
        val url = "https://api.tvmaze.com"
        return TvMazeService(url, httpClient, gson)
    }

    @Provides
    @Inject
    fun provideTvMazeRepository(tvMazeService: TvMazeService): TvMazeRepository {
        return TvMazeRepository(tvMazeService)
    }

    @Provides
    @Inject
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideGsonParser(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Inject
    fun provideBaseUrl(): String {
        return "https://api.tvmaze.com"
    }
}