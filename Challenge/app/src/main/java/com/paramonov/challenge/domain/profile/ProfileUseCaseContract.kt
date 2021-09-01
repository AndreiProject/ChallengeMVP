package com.paramonov.challenge.domain.profile

import androidx.lifecycle.LiveData
import com.paramonov.challenge.data.repository.remote.firebase.model.User

interface ProfileUseCaseContract {
    fun updateUser(user: User)
    fun getUser(): LiveData<User>
}