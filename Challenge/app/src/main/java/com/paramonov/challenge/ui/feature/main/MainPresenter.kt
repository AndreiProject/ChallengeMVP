package com.paramonov.challenge.ui.feature.main

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import com.paramonov.challenge.domain.authorization.AuthorizationUseCaseContract
import com.paramonov.challenge.ui.feature.main.MainPresenterContract.*
import com.paramonov.challenge.ui.navigation.IScreens
import javax.inject.Inject

class MainPresenter : MvpPresenter<View>(), Presenter {

    @Inject lateinit var useCase: AuthorizationUseCaseContract
    @Inject lateinit var router: Router
    @Inject lateinit var screens: IScreens

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        router.newRootScreen(screens.navigateToCategoryListFragment())
    }

    override fun logOut() {
        useCase.logOut()
        router.newRootScreen(screens.navigateToLoginActivity())
    }

    override fun navigateToSettings() {
        router.navigateTo(screens.navigateToSettingsFragment())
    }

    override fun navigateToStatistics() {
        router.newRootScreen(screens.navigateToStatisticsFragment())
    }

    override fun navigateToCollection() {
        router.newRootScreen(screens.navigateToCollectionFragment())
    }

    override fun navigateToCategoryList() {
        router.newRootScreen(screens.navigateToCategoryListFragment())
    }

    override fun navigateToPlanner() {
        router.newRootScreen(screens.navigateToPlannerFragment())
    }
}