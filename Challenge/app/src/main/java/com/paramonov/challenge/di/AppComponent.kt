package com.paramonov.challenge.di

import com.paramonov.challenge.di.module.*
import com.paramonov.challenge.ui.feature.category.CategoryPresenter
import com.paramonov.challenge.ui.feature.category_list.CategoryListPresenter
import com.paramonov.challenge.ui.feature.collection.CollectionListPresenter
import com.paramonov.challenge.ui.feature.login.*
import com.paramonov.challenge.ui.feature.main.*
import com.paramonov.challenge.ui.feature.settings.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CiceroneModule::class,
        LocalRepositoryModule::class,
        RemoteRepositoryModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent {

    // Activity
    fun inject(mainActivity: MainActivity)
    fun inject(loginActivity: LoginActivity)

    // Fragment
    fun inject(dialogFragment : PermissionDialogFragment)

    // Presenter
    fun inject(mainPresenter: MainPresenter)
    fun inject(loginPresenter: LoginPresenter)
    fun inject(loginPresenter: SettingsPresenter)
    fun inject(categoryListPresenter: CategoryListPresenter)
    fun inject(collectionListPresenter: CollectionListPresenter)
    fun inject(categoryPresenter: CategoryPresenter)
}