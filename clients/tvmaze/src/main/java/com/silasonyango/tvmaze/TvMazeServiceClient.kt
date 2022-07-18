package com.silasonyango.tvmaze

import com.silasonyango.common.Resource
import com.silasonyango.tvmaze.models.ShowResponseModel

interface TvMazeServiceClient {
    suspend fun fetchShowsByPage(page: Int): Resource<ShowResponseModel?>
}