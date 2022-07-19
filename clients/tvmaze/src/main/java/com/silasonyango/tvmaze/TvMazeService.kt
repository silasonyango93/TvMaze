package com.silasonyango.tvmaze

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.silasonyango.common.Resource
import com.silasonyango.tvmaze.models.ShowResponseModel
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume

class TvMazeService  @Inject constructor(
    private val baseUrl: String, private val httpClient: OkHttpClient, private val gson: Gson,
) {
    private val basePath = "/shows"
    suspend fun fetchShowsByPage(page: Int): Resource<List<ShowResponseModel>?>? =
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
                            "An error occurred while fetching shows",
                            null
                        )
                    )
                }

                override fun onResponse(call: Call, resp: Response) {
                    when {
                        resp.isSuccessful -> {
                            val responseType = TypeToken.getParameterized(
                                List::class.java,
                                ShowResponseModel::class.java
                            ).type

                            val showsList = gson.fromJson<List<ShowResponseModel>>(
                                resp.body()?.string(),
                                responseType
                            )
                            continuation.resume(Resource.success(showsList))
                        }
                        resp.code() == 422 -> {
                            continuation.resume(Resource.success(null))
                        }
                        else -> {
                            continuation.resume(
                                Resource.error(
                                    "An error occurred while fetching shows",
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