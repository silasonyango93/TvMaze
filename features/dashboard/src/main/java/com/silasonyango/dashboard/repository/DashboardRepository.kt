package com.silasonyango.dashboard.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.silasonyango.common.Resource
import com.silasonyango.tvmaze.TvMazeService
import com.silasonyango.tvmaze.models.ShowResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DashboardRepository @Inject constructor(private val dashboardService: DashboardService) {
    fun fetchShowsByPage(page: Int) = flow {
        emit(Resource.loading(null))
        emit(dashboardService.fetchShowsByPage(page))
    }.flowOn(Dispatchers.IO)
}