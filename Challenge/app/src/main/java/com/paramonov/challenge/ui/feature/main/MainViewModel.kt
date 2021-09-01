package com.paramonov.challenge.ui.feature.main

import androidx.lifecycle.ViewModel
import com.paramonov.challenge.domain.authorization.*

class MainViewModel(private val useCase: AuthorizationUseCaseContract) : ViewModel() {

    fun logOut() {
        useCase.logOut()
    }
}