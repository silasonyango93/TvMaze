package com.silasonyango.dashboard.viewmodel

import com.silasonyango.tvmaze.TvMazeRepository
import javax.inject.Inject

class DashboardViewModelProvider @Inject constructor(private val tvMazeRepository: TvMazeRepository) {
    companion object {
        var dashBoardViewModel: DashboardViewModel? = null
    }

    fun provideDashboardViewModel(): DashboardViewModel {
        val existing =
            dashBoardViewModel
        return if (existing != null) {
            existing
        } else {
            val new =
                DashboardViewModel(tvMazeRepository)
            dashBoardViewModel = new
            new
        }
    }
}