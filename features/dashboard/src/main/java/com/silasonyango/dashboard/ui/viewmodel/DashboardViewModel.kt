package com.silasonyango.dashboard.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silasonyango.common.Resource
import com.silasonyango.tvmaze.TvMazeRepository
import com.silasonyango.tvmaze.models.ShowResponseModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val tvMazeRepository: TvMazeRepository): ViewModel() {

    val showsResponse: LiveData<Resource<List<ShowResponseModel>?>?> =
        MutableLiveData(null)

    fun fetchShowsByPage(page: Int) {
        viewModelScope.launch {
            tvMazeRepository.fetchShowsByPage(page).collect { setShowsResponse(it) }
        }
    }

    fun setShowsResponse(showsResponse: Resource<List<ShowResponseModel>?>?) {
        (this.showsResponse as MutableLiveData).value = showsResponse
    }
}