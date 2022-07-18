package com.silasonyango.dashboard.dagger

import com.silasonyango.dashboard.ui.destinations.fragments.DashboardFragment
import dagger.Subcomponent

@Subcomponent
interface DashboardComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): DashboardComponent
    }

    fun inject(dashboardFragment: DashboardFragment)
}