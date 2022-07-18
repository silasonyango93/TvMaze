package com.silasonyango.tvmaze

import com.google.gson.Gson
import com.silasonyango.common.Resource
import com.silasonyango.tvmaze.models.ShowResponseModel
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resume

class TvMazeServiceClientImpl(
    private val baseUrl: String, private val httpClient: OkHttpClient, private val gson: Gson,
): TvMazeServiceClient {
    private val basePath = "/shows"
    override suspend fun fetchShowsByPage(page: Int): Resource<ShowResponseModel?> =
        suspendCancellableCoroutine { continuation ->
            val queryUrl = HttpUrl.get("$baseUrl$basePath")
                .newBuilder()
                .addQueryParameter("page", page.toString())
                .build()

            val request = Request.Builder()
                .url(queryUrl)
                .header("Accepts", "application/json")
                .build()

            val responseCallback = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resume(
                        Resource.error(
                            "An error occurred while fetching eSlip details",
                            null
                        )
                    )
                }

                override fun onResponse(call: Call, resp: Response) {
                    when {
                        resp.isSuccessful -> {
                            val responseData =
                                gson.fromJson(resp.body()?.string(), ShowResponseModel::class.java)
                            continuation.resume(Resource.success(responseData))
                        }
                        resp.code() == 422 -> {
                            continuation.resume(Resource.success(null))
                        }
                        else -> {
                            continuation.resume(
                                Resource.error(
                                    "An error occurred while fetching eSlip details",
                                    null
                                )
                            )
                        }
                    }
                }
            }

            val call = httpClient.newCall(request)
            call.enqueue(responseCallback)
            continuation.invokeOnCancellation { call.cancel() }
        }
}