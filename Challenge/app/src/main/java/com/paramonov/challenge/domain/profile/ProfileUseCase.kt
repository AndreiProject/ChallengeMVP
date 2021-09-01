package com.paramonov.challenge.domain.profile

import androidx.lifecycle.LiveData
import com.paramonov.challenge.data.repository.remote.RemoteRepository
import com.paramonov.challenge.data.repository.remote.firebase.model.User

class ProfileUseCase(private val rmRepository: RemoteRepository) :
    ProfileUseCaseContract {

    override fun updateUser(user: User) {
        rmRepository.updateUser(user)
    }

    override fun getUser(): LiveData<User> {
        return rmRepository.getUser()
    }
}