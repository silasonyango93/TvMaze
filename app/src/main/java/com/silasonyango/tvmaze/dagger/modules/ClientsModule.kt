package com.silasonyango.tvmaze.dagger.modules

import android.app.Application
import com.google.gson.Gson
import com.silasonyango.tvmaze.TvMazeServiceClient
import com.silasonyango.tvmaze.TvMazeServiceClientImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Module
class ClientsModule {

    @Provides
    @Inject
    fun provideIntegrationServiceClient(
        httpClient: OkHttpClient,
        gson: Gson,
    ): TvMazeServiceClient {
        val url = "https://api.tvmaze.com"
        return TvMazeServiceClientImpl(url, httpClient, gson)
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
}