package com.paramonov.challenge

import android.app.Application
import com.paramonov.challenge.di.*
import com.paramonov.challenge.di.module.LocalRepositoryModule

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .localRepositoryModule(LocalRepositoryModule(this))
            .build()
    }
}