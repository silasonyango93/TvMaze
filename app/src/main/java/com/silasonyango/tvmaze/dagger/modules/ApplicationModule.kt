package com.silasonyango.tvmaze.dagger.modules

import android.app.Application
import com.silasonyango.tvmaze.ui.MainActivity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Inject

@Module
interface ApplicationModule {
    @ContributesAndroidInjector
    fun contributeMainActivityAndroidInjector(): MainActivity
}