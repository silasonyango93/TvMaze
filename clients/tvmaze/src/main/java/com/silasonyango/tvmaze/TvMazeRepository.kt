package com.silasonyango.tvmaze

import com.silasonyango.common.Resource
import com.silasonyango.tvmaze.models.ShowResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TvMazeRepository @Inject constructor(private val tvMazeService: TvMazeService) {
    fun fetchShowsByPage(page: Int) = flow {
        emit(Resource.loading(null))
        emit(tvMazeService.fetchShowsByPage(page))
    }.flowOn(Dispatchers.IO)
}