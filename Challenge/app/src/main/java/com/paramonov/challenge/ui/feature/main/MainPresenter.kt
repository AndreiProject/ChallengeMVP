package com.paramonov.challenge.ui.feature.main

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import com.paramonov.challenge.domain.authorization.AuthorizationUseCaseContract
import com.paramonov.challenge.ui.feature.main.MainPresenterContract.*
import com.paramonov.challenge.ui.navigation.AndroidScreens
import com.paramonov.challenge.ui.navigation.IScreens
import org.koin.java.KoinJavaComponent

class MainPresenter(private val useCase: AuthorizationUseCaseContract) : MvpPresenter<View>(),
    Presenter {

    private val router: Router by KoinJavaComponent.inject(Router::class.java)
    private val screens: IScreens by KoinJavaComponent.inject(AndroidScreens::class.java)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        router.newRootScreen(screens.navigateToStatisticsFragment())
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