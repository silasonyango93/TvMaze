package com.silasonyango.tvmaze

import android.app.Application
import com.silasonyango.tvmaze.dagger.DaggerApplicationComponent
import dagger.android.DispatchingAndroidInjector
import dagger.internal.InstanceFactory.create
import dagger.internal.MapFactory.builder
import javax.inject.Inject

class MyApplication: Application() {
    // Reference to the application graph that is used across the whole app
    private val appComponent = DaggerApplicationComponent.factory().newInstance(this)

    @Inject
    lateinit var dispatchingInjector: DispatchingAndroidInjector<Any>

    init {
        appComponent.inject(this)
    }
}