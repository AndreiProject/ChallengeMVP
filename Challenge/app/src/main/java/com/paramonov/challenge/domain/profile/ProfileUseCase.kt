package com.paramonov.challenge.domain.profile

import com.paramonov.challenge.data.repository.remote.RemoteRepository
import com.paramonov.challenge.data.repository.remote.firebase.model.User
import io.reactivex.rxjava3.core.Flowable

class ProfileUseCase(private val rmRepository: RemoteRepository) :
    ProfileUseCaseContract {

    override fun updateUser(user: User) {
        rmRepository.updateUser(user)
    }

    override fun getUser(): Flowable<User> {
        return rmRepository.getUser()
    }
}