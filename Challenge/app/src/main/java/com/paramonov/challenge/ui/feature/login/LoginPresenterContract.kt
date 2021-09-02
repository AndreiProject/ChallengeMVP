package com.paramonov.challenge.ui.feature.login

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface LoginPresenterContract {

    interface Presenter {
        fun auth(email: String, password: String)
    }

    @AddToEndSingle
    interface View : MvpView {
        fun init()
        fun navigateToMainActivity()
        fun loginWarnError()
        fun passwordWarnError()
        fun showToast(message: String)
    }
}