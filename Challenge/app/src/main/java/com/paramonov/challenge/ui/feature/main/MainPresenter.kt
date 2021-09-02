package com.paramonov.challenge.ui.feature.main

import moxy.MvpPresenter
import com.paramonov.challenge.domain.authorization.AuthorizationUseCaseContract
import com.paramonov.challenge.ui.feature.main.MainPresenterContract.*

class MainPresenter(private val useCase: AuthorizationUseCaseContract) : MvpPresenter<View>(),
    Presenter {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    override fun logOut() {
        useCase.logOut()
        viewState.navigateToLogin()
    }

    override fun navigateToSettings() {
        viewState.navigateToSettings()
    }
}