package com.paramonov.challenge.ui.feature.settings

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface SettingsPresenterContract {

    interface Presenter {
        fun updateUser(name: String, surname: String)
    }

    @AddToEndSingle
    interface View : MvpView {
        fun init()
        fun nameWarnError()
        fun surnameWarnError()
        fun showToast(message: String)
        fun setName(name: String)
        fun setSurname(surname: String)
    }
}