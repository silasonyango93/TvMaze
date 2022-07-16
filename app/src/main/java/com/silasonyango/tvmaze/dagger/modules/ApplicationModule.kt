package com.silasonyango.tvmaze.dagger.modules

import com.silasonyango.tvmaze.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ApplicationModule {
    @ContributesAndroidInjector
    fun contributeMainActivityAndroidInjector(): MainActivity
}