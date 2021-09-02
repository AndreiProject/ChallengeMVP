package com.paramonov.challenge.domain.authorization

import com.paramonov.challenge.data.repository.remote.RemoteRepository
import io.reactivex.rxjava3.core.Single

class AuthorizationUseCase(private val rmRepository: RemoteRepository) :
    AuthorizationUseCaseContract {

    override fun checkAuth() = rmRepository.checkAuth()

    override fun auth(email: String, password: String): Single<Boolean> {
        return rmRepository.auth(email, password)
    }

    override fun logOut() {
        rmRepository.logOut()
    }
}