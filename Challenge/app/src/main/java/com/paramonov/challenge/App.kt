package com.paramonov.challenge

import android.app.Application
import com.paramonov.challenge.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                remoteRepositoryModule,
                localRepositoryModule,
                useCaseModule,
                categoryModule,
                categoryListViewModel,
                collectionViewModel,
                loginViewModel,
                mainViewModel,
                settingsViewModel
            )
        }
    }
}