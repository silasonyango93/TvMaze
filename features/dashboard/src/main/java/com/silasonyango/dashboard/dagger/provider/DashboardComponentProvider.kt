package com.silasonyango.dashboard.dagger.provider

import com.silasonyango.dashboard.dagger.DashboardComponent

interface DashboardComponentProvider {
    fun provideDashboardComponent(): DashboardComponent
}