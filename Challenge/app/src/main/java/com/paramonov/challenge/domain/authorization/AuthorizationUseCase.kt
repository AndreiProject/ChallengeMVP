package com.paramonov.challenge.domain.authorization

import androidx.lifecycle.LiveData
import com.paramonov.challenge.data.repository.remote.RemoteRepository
import com.paramonov.challenge.ui.feature.login.Result

class AuthorizationUseCase(private val rmRepository: RemoteRepository) :
    AuthorizationUseCaseContract {

    override fun checkAuth() = rmRepository.checkAuth()

    override fun auth(email: String, password: String): LiveData<Result> {
        return rmRepository.auth(email, password)
    }

    override fun logOut() {
        rmRepository.logOut()
    }
}