package com.paramonov.challenge.domain.authorization

import io.reactivex.rxjava3.core.Single

interface AuthorizationUseCaseContract {
    fun checkAuth(): Boolean
    fun auth(email: String, password: String): Single<Boolean>
    fun logOut()
}