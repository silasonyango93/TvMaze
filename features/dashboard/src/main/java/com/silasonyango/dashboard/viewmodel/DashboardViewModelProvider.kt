package com.silasonyango.dashboard.viewmodel

class DashboardViewModelProvider {
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
                DashboardViewModel()
            dashBoardViewModel = new
            new
        }
    }
}