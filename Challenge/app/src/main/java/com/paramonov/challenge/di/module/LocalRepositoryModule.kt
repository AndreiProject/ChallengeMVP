package com.paramonov.challenge.di.module

import android.content.Context
import com.paramonov.challenge.data.repository.local.*
import com.paramonov.challenge.data.repository.local.platform.*
import com.paramonov.challenge.data.repository.local.room.*
import com.paramonov.challenge.data.source.room.AppRoomDatabase
import dagger.*
import javax.inject.Singleton

@Module
class LocalRepositoryModule(val context: Context) {

    @Singleton
    @Provides
    fun provideRoomDatabase()
            : AppRoomDatabase = AppRoomDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideRoomRepository(roomDatabase: AppRoomDatabase)
            : RoomRepository = AppRoomRepository(roomDatabase)

    @Singleton
    @Provides
    fun providePlatformRepository()
            : PlatformRepository = AppPlatformRepository(context)

    @Singleton
    @Provides
    fun provideLocalRepository(
        rmRepository: RoomRepository,
        plRepository: PlatformRepository
    )
            : LocalRepository = AppLocalRepository(rmRepository, plRepository)
}