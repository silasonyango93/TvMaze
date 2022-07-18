package com.silasonyango.tvmaze.dagger.modules

import com.silasonyango.dashboard.dagger.DashboardComponent
import dagger.Module

@Module(subcomponents = [
    DashboardComponent::class
])
class SubComponentsModule