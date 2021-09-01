package com.paramonov.challenge.domain.authorization

import androidx.lifecycle.LiveData
import com.paramonov.challenge.ui.feature.login.Result

interface AuthorizationUseCaseContract {
    fun checkAuth(): Boolean
    fun auth(email: String, password: String): LiveData<Result>
    fun logOut()
}