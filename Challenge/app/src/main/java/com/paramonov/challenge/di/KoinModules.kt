package com.paramonov.challenge.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.paramonov.challenge.data.repository.local.*
import com.paramonov.challenge.data.repository.local.platform.*
import com.paramonov.challenge.data.repository.local.room.*
import com.paramonov.challenge.data.repository.remote.*
import com.paramonov.challenge.data.repository.remote.firebase.*
import com.paramonov.challenge.data.repository.remote.retrofit.*
import com.paramonov.challenge.data.source.retrofit.RetrofitClient
import com.paramonov.challenge.data.source.room.AppRoomDatabase
import com.paramonov.challenge.domain.authorization.*
import com.paramonov.challenge.domain.content.*
import com.paramonov.challenge.domain.profile.*
import com.paramonov.challenge.ui.feature.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.*

val remoteRepositoryModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseDatabase.getInstance() }
    single { AppFirebaseRepository(get(), get()) } bind FirebaseRepository::class
    single { RetrofitClient.getService() }
    single { AppRetrofitRepository(get()) } bind RetrofitRepository::class
    single { AppRemoteRepository(get(), get()) } bind RemoteRepository::class
}

val localRepositoryModule = module {
    single { AppRoomDatabase.getInstance(androidContext()) }
    single { AppRoomRepository(get()) } bind RoomRepository::class
    single { AppPlatformRepository(androidContext()) } bind PlatformRepository::class
    single { AppLocalRepository(get(), get()) } bind LocalRepository::class
}

val useCaseModule = module {
    factory { AuthorizationUseCase(get()) } bind AuthorizationUseCaseContract::class
    factory { ContentUseCase(get(), get()) } bind ContentUseCaseContract::class
    factory { ProfileUseCase(get()) } bind ProfileUseCaseContract::class
}

val categoryModule = module {
    viewModel { parameters -> CategoryViewModel(parameters.get(), get()) }
}
val categoryListViewModel = module {
    viewModel { CategoryListViewModel(get()) }
}
val collectionViewModel = module {
    viewModel { CollectionViewModel(get()) }
}
val loginViewModel = module {
    viewModel { LoginViewModel(get()) }
}
val mainViewModel = module {
    viewModel { MainViewModel(get()) }
}
val settingsViewModel = module {
    viewModel { SettingsViewModel(get()) }
}