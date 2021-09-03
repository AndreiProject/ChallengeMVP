package com.paramonov.challenge.di.module

import com.paramonov.challenge.data.repository.local.LocalRepository
import com.paramonov.challenge.data.repository.remote.RemoteRepository
import com.paramonov.challenge.domain.authorization.*
import com.paramonov.challenge.domain.content.*
import com.paramonov.challenge.domain.profile.*
import dagger.*

@Module
class UseCaseModule {

    @Provides
    fun provideAuthorizationUseCase(rmRepository: RemoteRepository)
            : AuthorizationUseCaseContract = AuthorizationUseCase(rmRepository)

    @Provides
    fun provideContentUseCase(
        rmRepository: RemoteRepository,
        lcRepository: LocalRepository
    )
            : ContentUseCaseContract = ContentUseCase(rmRepository, lcRepository)

    @Provides
    fun provideProfileUseCase(
        rmRepository: RemoteRepository
    )
            : ProfileUseCaseContract = ProfileUseCase(rmRepository)
}