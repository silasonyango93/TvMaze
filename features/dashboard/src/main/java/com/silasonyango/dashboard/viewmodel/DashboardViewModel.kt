package com.silasonyango.dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silasonyango.tvmaze.TvMazeRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

class DashboardViewModel @Inject constructor(val tvMazeRepository: TvMazeRepository): ViewModel() {

    fun fetchShowsByPage(page: Int) {
        viewModelScope.launch {
            tvMazeRepository.fetchShowsByPage(page)
        }
    }
}