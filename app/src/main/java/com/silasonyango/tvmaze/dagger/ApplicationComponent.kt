package com.silasonyango.tvmaze.dagger

import android.app.Application
import android.content.Context
import com.silasonyango.tvmaze.MyApplication
import com.silasonyango.tvmaze.dagger.modules.ApplicationModule
import com.silasonyango.tvmaze.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import dagger.android.AndroidInjectionModule

// Definition of the root application graph
@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ApplicationModule::class
])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun newInstance(@BindsInstance application: Application): ApplicationComponent
    }

    fun inject(application: MyApplication)
}