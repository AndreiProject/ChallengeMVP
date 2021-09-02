package com.paramonov.challenge.ui.feature.main

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface MainPresenterContract {

    interface Presenter {
        fun logOut()
        fun navigateToSettings()
    }

    @AddToEndSingle
    interface View : MvpView {
        fun init()
        fun navigateToLogin()
        fun navigateToSettings()
    }
}