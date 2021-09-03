package com.paramonov.challenge.di.module

import com.github.terrakok.cicerone.*
import com.paramonov.challenge.ui.navigation.*
import dagger.*
import javax.inject.Singleton

@Module
class CiceroneModule {
    var cicerone: Cicerone<Router> = Cicerone.create()

    @Singleton
    @Provides
    fun provideNavigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()

    @Singleton
    @Provides
    fun provideRouter(): Router = cicerone.router

    @Singleton
    @Provides
    fun provideScreens(): IScreens = AndroidScreens()
}