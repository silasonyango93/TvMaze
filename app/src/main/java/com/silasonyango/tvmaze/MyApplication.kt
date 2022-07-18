package com.silasonyango.tvmaze

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.silasonyango.dashboard.dagger.DashboardComponent
import com.silasonyango.dashboard.dagger.provider.DashboardComponentProvider
import com.silasonyango.tvmaze.dagger.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.internal.InstanceFactory.create
import dagger.internal.MapFactory.builder
import javax.inject.Inject

open class MyApplication : MultiDexApplication(), DashboardComponentProvider, HasAndroidInjector {
    // Reference to the application graph that is used across the whole app
    private val appComponent = DaggerApplicationComponent.factory().newInstance(this)

    @Inject
    lateinit var dispatchingInjector: DispatchingAndroidInjector<Any>

    init {
        appComponent.inject(this)
    }

    override fun provideDashboardComponent(): DashboardComponent =
        appComponent.dashboardComponent().create()

    override fun androidInjector(): AndroidInjector<Any> = dispatchingInjector
}