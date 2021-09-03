package com.paramonov.challenge.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.paramonov.challenge.data.repository.remote.*
import com.paramonov.challenge.data.repository.remote.firebase.*
import com.paramonov.challenge.data.repository.remote.retrofit.*
import com.paramonov.challenge.data.repository.remote.retrofit.service.RetrofitService
import com.paramonov.challenge.data.source.retrofit.RetrofitClient
import dagger.*
import javax.inject.Singleton

@Module
class RemoteRepositoryModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseRepository(auth: FirebaseAuth, fbDatabase: FirebaseDatabase)
            : FirebaseRepository = AppFirebaseRepository(auth, fbDatabase)

    @Singleton
    @Provides
    fun provideRetrofitClient(): RetrofitService = RetrofitClient.getService()

    @Singleton
    @Provides
    fun provideRetrofitRepository(service: RetrofitService)
            : RetrofitRepository = AppRetrofitRepository(service)

    @Singleton
    @Provides
    fun provideRemoteRepository(
        fbRepository: FirebaseRepository,
        rfRepository: RetrofitRepository
    )
            : RemoteRepository = AppRemoteRepository(fbRepository, rfRepository)
}